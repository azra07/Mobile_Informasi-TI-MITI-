package com.putrinadya.miti.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun RegistrationPopUp(
    event: Event,
    isJoined: Boolean,
    onClose: () -> Unit,
    onJoinClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    // KONVERSI: Mengubah String Hex warna dari model menjadi Color Jetpack Compose
    val categoryColor = remember(event.categoryColorHex) {
        try {
            Color(android.graphics.Color.parseColor(event.categoryColorHex))
        } catch (e: Exception) {
            MitiGray
        }
    }

    // Parsing Hari & Tanggal Otomatis dalam Bahasa Indonesia murni
    val formattedDate = remember(event.fullDate) {
        try {
            val inputFormat = SimpleDateFormat("M/d/yyyy", Locale.US)
            val dateObj = inputFormat.parse(event.fullDate)
            if (dateObj != null) {
                val outputFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale("in", "ID"))
                outputFormat.format(dateObj)
            } else {
                "${event.dayMonth} ${event.year}, 2026"
            }
        } catch (e: Exception) {
            "${event.dayMonth} ${event.year}, 2026"
        }
    }

    // Memformat jam agar selalu berakhiran "WITA"
    val formattedTime = remember(event.time) {
        if (event.time.contains("WITA", ignoreCase = true)) event.time else "${event.time} WITA"
    }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState) // Aktifkan scroll saat posisi landscape
            ) {
                // Header Banner Atas dengan Struktur Vertikal Rapi
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(categoryColor)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Berikan jarak vertikal antar baris
                ) {
                    // BARIS 1 (PALING ATAS): Khusus Tombol Silang (X) Penutup di Kanan Atas
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.Black.copy(alpha = 0.2f), CircleShape)
                                .clickable { onClose() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Tutup",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    // BARIS 2 (DI BAWAHNYA): Judul Kegiatan (Kiri) & Jenis Kegiatan (Kanan)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = event.title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = event.category,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(
                                    Color.Black.copy(alpha = 0.2f),
                                    RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Bagian Informasi Detail Kegiatan
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // BARIS TANGGAL ACARA (Lebar penuh)
                    RowInfoBox(
                        title = "TANGGAL ACARA",
                        value = formattedDate,
                        icon = "📅",
                        modifier = Modifier.fillMaxWidth()
                    )

                    // BARIS WAKTU MULAI
                    RowInfoBox(
                        title = "WAKTU MULAI",
                        value = formattedTime,
                        icon = "🕒",
                        modifier = Modifier.fillMaxWidth()
                    )

                    // BARIS LOKASI KEGIATAN
                    RowInfoBox(
                        title = "LOKASI KEGIATAN",
                        value = event.location,
                        icon = "📍",
                        modifier = Modifier.fillMaxWidth()
                    )

                    // BARIS KUOTA TERSEDIA
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                                RoundedCornerShape(12.dp)
                            )
                            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("👥", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Kuota Tersedia",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp
                                )
                            }
                            Text(
                                text = "${event.currentParticipants} / ${event.maxParticipants}",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Deskripsi Kegiatan
                    Text(
                        text = "Deskripsi Kegiatan",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = event.description.ifEmpty { "Tidak ada deskripsi untuk kegiatan ini." },
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Tombol Aksi Daftar / Batal Daftar (Warna Hijau Terang saat terdaftar)
                    Button(
                        onClick = onJoinClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isJoined) Color(0xFF00E676) else MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text(
                            text = if (isJoined) "Sudah Terdaftar ✓" else "Daftar Kegiatan",
                            color = if (isJoined) Color.White else MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

// Component pembantu kotak info detail
@Composable
private fun RowInfoBox(title: String, value: String, icon: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.background,
                RoundedCornerShape(12.dp)
            )
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(icon, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(title, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), fontSize = 10.sp)
                Text(
                    value,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}