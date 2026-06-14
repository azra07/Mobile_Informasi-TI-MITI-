package com.putrinadya.miti.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.ui.theme.* // Mengimpor tema warna kustom Anda

@Composable
fun UpcomingActivityCard(
    event: Event,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // KONVERSI: Mengubah String Hex warna dari model menjadi Color Jetpack Compose
    val categoryColor = remember(event.categoryColorHex) {
        try {
            Color(android.graphics.Color.parseColor(event.categoryColorHex))
        } catch (e: Exception) {
            MitiGray
        }
    }

    // Memformat jam agar selalu diakhiri dengan WITA secara rapi
    val formattedTime = remember(event.time) {
        if (event.time.contains("WITA", ignoreCase = true)) event.time else "${event.time} WITA"
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), // Latar belakang abu terang di Light Mode
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // PERBAIKAN: Kotak penampung tanggal (Tanggal ATAS menonjol, Tahun BAWAH mengecil)
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), // Cyan transparan lembut
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Teks Atas (Tanggal & Bulan) dibuat besar dan tebal (menonjol)
                    Text(
                        text = event.dayMonth,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary, // Berwarna Cyan murni
                        fontWeight = FontWeight.Bold
                    )
                    // Teks Bawah (Tahun) dibuat kecil dan abu-abu lembut
                    Text(
                        text = event.year,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) // Berwarna abu/hitam pudar di Light Mode
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface // Teks warna Hitam di Light Mode
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = formattedTime, // Waktu otomatis berakhiran WITA
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(" • ", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
                    Text(
                        text = event.location,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Kategori Badge di ujung kanan
            Box(
                modifier = Modifier
                    .background(
                        categoryColor.copy(alpha = 0.2f),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = event.category,
                    fontSize = 11.sp,
                    color = categoryColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}