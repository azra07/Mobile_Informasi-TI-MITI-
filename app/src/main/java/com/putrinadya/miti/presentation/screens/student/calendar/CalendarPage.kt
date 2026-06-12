package com.putrinadya.miti.presentation.screens.student.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.putrinadya.miti.presentation.screens.student.dashboard.StudentDashboardViewModel
import com.putrinadya.miti.ui.theme.* // Mengimpor tema warna kustom Anda

@Composable
fun CalendarPage(
    viewModel: StudentDashboardViewModel, // Berbagi instance ViewModel dengan Dashboard agar sinkron
    calendarViewModel: CalendarViewModel = viewModel()
) {
    val uiState = calendarViewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MitiNavy) // Menggunakan MitiNavy kustom Anda
            .padding(16.dp)
    ) {
        Text(
            text = "Calendar",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MitiWhite, // Menggunakan MitiWhite
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("<", fontSize = 20.sp, color = MitiWhite, fontWeight = FontWeight.Bold)
            Text(uiState.currentMonth, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MitiWhite)
            Text(">", fontSize = 20.sp, color = MitiWhite, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MitiCard), // Menggunakan MitiCard kustom Anda
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    daysOfWeek.forEach { day ->
                        Text(
                            text = day,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            color = MitiGray, // Menggunakan MitiGray
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                val emptySlotsBefore = 3
                val daysInApril = 30
                val totalCells = emptySlotsBefore + daysInApril

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.height(240.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(totalCells) { index ->
                        if (index < emptySlotsBefore) {
                            Box(modifier = Modifier.size(36.dp))
                        } else {
                            val dayNumber = index - emptySlotsBefore + 1
                            val isSelected = dayNumber == uiState.selectedDay

                            // MENCARI TITIK EVENT SECARA DINAMIS DARI USE CASE
                            val dayEvent = uiState.events.find {
                                it.year.toIntOrNull() == dayNumber && it.dayMonth == "Apr"
                            }

                            // KONVERSI: Mengubah String Hex warna dari model menjadi Color Jetpack Compose
                            val eventColor = dayEvent?.let { event ->
                                try {
                                    Color(android.graphics.Color.parseColor(event.categoryColorHex))
                                } catch (e: Exception) {
                                    MitiGray // Warna default jika gagal parsing
                                }
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .background(
                                            color = if (isSelected) MitiCyan.copy(alpha = 0.2f) else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .clickable { calendarViewModel.onDaySelected(dayNumber) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = dayNumber.toString(),
                                        color = if (isSelected) MitiCyan else MitiWhite,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 14.sp
                                    )
                                }

                                // Menggambar titik warna kategori event secara dinamis
                                if (eventColor != null) {
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Box(
                                        modifier = Modifier
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