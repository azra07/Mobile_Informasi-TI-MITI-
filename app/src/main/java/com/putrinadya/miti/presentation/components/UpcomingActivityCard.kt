package com.putrinadya.miti.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val categoryColor = try {
        Color(android.graphics.Color.parseColor(event.categoryColorHex))
    } catch (e: Exception) {
        MitiGray // Warna default jika gagal parsing
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MitiCard), // Menggunakan MitiCard
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        Color(0xFF142233), // Warna latar belakang tanggal
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(event.dayMonth, fontSize = 11.sp, color = MitiGray) // Menggunakan MitiGray
                    Text(
                        event.year,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MitiWhite // Menggunakan MitiWhite
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    event.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MitiWhite // Menggunakan MitiWhite
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(event.time, fontSize = 11.sp, color = MitiGray) // Menggunakan MitiGray
                    Text(" • ", fontSize = 11.sp, color = MitiGray)
                    Text(
                        event.location,
                        fontSize = 11.sp,
                        color = MitiGray, // Menggunakan MitiGray
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(
                        categoryColor.copy(alpha = 0.2f), // Menggunakan categoryColor hasil konversi
                        RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = event.category,
                    fontSize = 11.sp,
                    color = categoryColor, // Menggunakan categoryColor hasil konversi
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}