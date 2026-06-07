package com.putrinadya.miti.presentation.screens.student.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Calender() {
    val backgroundColor = Color(0xFF030A16)
    val cardColor = Color(0xFF091522)
    val primaryCyan = Color(0xFF00E5FF)
    val textWhite = Color(0xFFFFFFFF)

    Column(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Text(
            text = "Calendar",
            fontSize = 24.sp,
            fontWeight = FontWeight.Companion.Bold,
            color = textWhite,
            modifier = Modifier.Companion.padding(bottom = 24.dp)
        )

        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            Text("<", fontSize = 20.sp, color = textWhite, fontWeight = FontWeight.Companion.Bold)
            Text(
                "April 2026",
                fontSize = 18.sp,
                fontWeight = FontWeight.Companion.Bold,
                color = textWhite
            )
            Text(">", fontSize = 20.sp, color = textWhite, fontWeight = FontWeight.Companion.Bold)
        }

        Spacer(modifier = Modifier.Companion.height(16.dp))

        Card(
            modifier = Modifier.Companion.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.Companion.padding(16.dp)) {
                val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    daysOfWeek.forEach { day ->
                        Text(
                            text = day,
                            modifier = Modifier.Companion.weight(1f),
                            textAlign = TextAlign.Companion.Center,
                            color = Color.Companion.Gray,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.Companion.height(12.dp))

                val emptySlotsBefore = 3
                val daysInApril = 30
                val totalCells = emptySlotsBefore + daysInApril

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.Companion.height(240.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(totalCells) { index ->
                        if (index < emptySlotsBefore) {
                            Box(modifier = Modifier.Companion.size(36.dp))
                        } else {
                            val dayNumber = index - emptySlotsBefore + 1
                            val isToday = dayNumber == 10

                            val eventColor = when (dayNumber) {
                                15 -> Color(0xFFC583FF) // Workshop Purple
                                18 -> Color(0xFF00B0FF) // Seminar Blue
                                20 -> Color(0xFF00E676) // Hackathon Green
                                22 -> Color(0xFF2979FF) // Webinar Dark Blue
                                25 -> Color(0xFFFFD600) // Competition Yellow
                                28 -> Color(0xFFC583FF) // Workshop Purple
                                else -> null
                            }

                            Column(
                                horizontalAlignment = Alignment.Companion.CenterHorizontally,
                                modifier = Modifier.Companion.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier.Companion
                                        .size(34.dp)
                                        .background(
                                            color = if (isToday) primaryCyan.copy(alpha = 0.2f) else Color.Companion.Transparent,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Companion.Center
                                ) {
                                    Text(
                                        text = dayNumber.toString(),
                                        color = if (isToday) primaryCyan else textWhite,
                                        fontWeight = if (isToday) FontWeight.Companion.Bold else FontWeight.Companion.Normal,
                                        fontSize = 14.sp
                                    )
                                }

                                if (eventColor != null) {
                                    Spacer(modifier = Modifier.Companion.height(3.dp))
                                    Box(
                                        modifier = Modifier.Companion
                                            .size(5.dp)
                                            .background(eventColor, CircleShape)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}