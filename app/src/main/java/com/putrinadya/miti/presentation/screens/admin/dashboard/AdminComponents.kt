package com.putrinadya.miti.presentation.screens.admin.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun StatCard(title: String, value: String, iconText: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(28.dp).background(MaterialTheme.colorScheme.onBackground, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(iconText, fontSize = 14.sp)
            }
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Text(title, fontSize = 9.sp, color = MaterialTheme.colorScheme.onBackground, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun AdminEventCard(
    event: Event,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    // KONVERSI: Mengubah String Hex warna dari model menjadi Color Jetpack Compose
    val categoryColor = try {
        Color(android.graphics.Color.parseColor(event.categoryColorHex))
    } catch (e: Exception) {
        MaterialTheme.colorScheme.onBackground
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, top = 12.dp, bottom = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Menu Titik Tiga (MoreVert) di sebelah kiri
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = MaterialTheme.colorScheme.onBackground)
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit Event", color = MaterialTheme.colorScheme.onBackground) },
                        onClick = { showMenu = false; onEdit() }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete Event", color = Color.Red) },
                        onClick = { showMenu = false; onDelete() }
                    )
                }
            }

            Spacer(modifier = Modifier.width(4.dp))

            // Kolom Konten Utama (Terbagi Menjadi 3 Baris Rapi)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // BARIS 1: Judul Event & Badge Jenis Event
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(categoryColor.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(event.category, fontSize = 10.sp, color = categoryColor, fontWeight = FontWeight.Bold)
                    }
                }

                // BARIS 2: Deteksi Hari Secara Otomatis Berdasarkan Input Tanggal
                val formattedDate = remember(event.fullDate) {
                    try {
                        val inputFormat = SimpleDateFormat("M/d/yyyy", Locale.US)
                        val dateObj = inputFormat.parse(event.fullDate)
                        if (dateObj != null) {
                            // Menggunakan Locale("in", "ID") agar nama hari selalu berbahasa Indonesia
                            val outputFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale("in", "ID"))
                            outputFormat.format(dateObj)
                        } else {
                            val actualYear = event.fullDate.split("/").lastOrNull() ?: "2026"
                            "Senin, ${event.dayMonth} ${event.year}, $actualYear"
                        }
                    } catch (e: Exception) {
                        val actualYear = event.fullDate.split("/").lastOrNull() ?: "2026"
                        "Senin, ${event.dayMonth} ${event.year}, $actualYear"
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📅 $formattedDate",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "🕒 ${event.time}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                // BARIS 3: Kapasitas Peserta & Lokasi Acara
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "👥 ${event.currentParticipants}/${event.maxParticipants}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "📍 ${event.location}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}