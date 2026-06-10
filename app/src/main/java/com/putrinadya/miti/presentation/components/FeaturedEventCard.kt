package com.putrinadya.miti.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.ui.theme.* // Mengimpor tema warna kustom Anda

@Composable
fun FeaturedEventCard(
    event: Event,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(260.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MitiCyan) // Menggunakan MitiCyan
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Text(
                    text = event.category.uppercase(),
                    fontSize = 11.sp,
                    color = MitiNavy, // Menggunakan MitiNavy
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            MitiWhite.copy(alpha = 0.3f), // Menggunakan MitiWhite
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = event.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MitiNavy, // Menggunakan MitiNavy
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.description,
                    fontSize = 12.sp,
                    color = MitiNavy.copy(alpha = 0.8f), // Menggunakan MitiNavy
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    event.time,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MitiNavy // Menggunakan MitiNavy
                )
                Text(
                    "👥 ${event.currentParticipants}/${event.maxParticipants}",
                    fontSize = 12.sp,
                    color = MitiNavy // Menggunakan MitiNavy
                )
            }
        }
    }
}