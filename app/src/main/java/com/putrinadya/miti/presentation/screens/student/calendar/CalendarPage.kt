package com.putrinadya.miti.presentation.screens.student.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.putrinadya.miti.presentation.screens.student.dashboard.StudentDashboardViewModel
import com.putrinadya.miti.R
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.ui.theme.MitiGray
import com.putrinadya.miti.ui.theme.MitiWhite
import java.util.Calendar

@Composable
fun CalendarPage(
    viewModel: StudentDashboardViewModel,
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val uiState = calendarViewModel.uiState

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // PERBAIKAN: Judul halaman otomatis hitam di Light Mode
            Text(
                text = stringResource(id = R.string.nav_calendar),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground, // Dinamis
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { calendarViewModel.previousMonth() }) {
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground // Dinamis
                    )
                }

                // PERBAIKAN: Sapaan nama bulan otomatis hitam di Light Mode
                Text(
                    text = uiState.currentMonthName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground // Dinamis
                )
                IconButton(onClick = { calendarViewModel.nextMonth() }) {
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground // Dinamis
                    )
                }
            }
        }

        // 3. KONTEN KALENDER DALAM CARD
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), // Latar belakang abu terang di Light Mode
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Nama Hari (Sun, Mon, dst)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
                        daysOfWeek.forEach { day ->
                            Text(
                                text = day,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), // Dinamis abu kontras
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Grid Hari
                    val emptySlotsBefore = uiState.firstDayOfWeek
                    val totalSlots = emptySlotsBefore + uiState.daysInMonth

                    // Membuat Grid secara manual agar bisa masuk ke LazyColumn
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        val rows = (totalSlots + 6) / 7
                        for (row in 0 until rows) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                for (col in 0 until 7) {
                                    val index = row * 7 + col
                                    val dayNumber = index - emptySlotsBefore + 1

                                    Box(
                                        modifier = Modifier.weight(1f),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (dayNumber in 1..uiState.daysInMonth) {
                                            // Mencari semua event di tanggal ini untuk titik warna
                                            val eventsOnThisDay = uiState.events.filter { event ->
                                                val dateParts = event.fullDate.split("/")
                                                val eventDay = dateParts.getOrNull(1)?.toIntOrNull()
                                                val eventMonth = dateParts.getOrNull(0)?.toIntOrNull()
                                                val currentMonth = uiState.displayDate.get(Calendar.MONTH) + 1

                                                eventDay == dayNumber && eventMonth == currentMonth
                                            }

                                            CalendarDayItem(
                                                day = dayNumber,
                                                isSelected = dayNumber == uiState.selectedDay,
                                                events = eventsOnThisDay,
                                                onClick = { calendarViewModel.onDaySelected(dayNumber) }
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

        // 4. HEADER TANGGAL TERPILIH
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "List Kegiatan", // Bahasa Indonesia murni
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground // Dinamis
            )
        }

        // 5. DAFTAR EVENT DI BAWAH KALENDER
        val selectedEvents = uiState.events.filter { event ->
            val dateParts = event.fullDate.split("/")
            val eventDay = dateParts.getOrNull(1)?.toIntOrNull()
            val eventMonth = dateParts.getOrNull(0)?.toIntOrNull()
            val currentMonth = uiState.displayDate.get(Calendar.MONTH) + 1
            eventDay == uiState.selectedDay && eventMonth == currentMonth
        }

        if (selectedEvents.isEmpty()) {
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp), contentAlignment = Alignment.Center) {
                    Text("Tidak ada kegiatan di tanggal ini.", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 14.sp)
                }
            }
        } else {
            items(selectedEvents) { event ->
                SelectedEventItem(event)
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

// ================= COMPONENT PEMBANTU: ITEM HARI KALENDER =================
@Composable
fun CalendarDayItem(
    day: Int,
    isSelected: Boolean,
    events: List<Event>,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.toString(),
                // PERBAIKAN: Jika dipilih, teks berwarna hitam kontras di atas Cyan. Jika biasa, berwarna dinamis onSurface
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 14.sp
            )
        }

        // Row Titik Warna
        Row(
            modifier = Modifier.padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            events.take(3).forEach { event ->
                val dotColor = try {
                    Color(android.graphics.Color.parseColor(event.categoryColorHex))
                } catch (e: Exception) { Color.Gray }

                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(dotColor, CircleShape)
                )
            }
        }
    }
}

// ================= COMPONENT PEMBANTU: ITEM EVENT YANG DIPILIH =================
@Composable
fun SelectedEventItem(event: Event) {
    val categoryColor = try {
        Color(android.graphics.Color.parseColor(event.categoryColorHex))
    } catch (e: Exception) { MaterialTheme.colorScheme.primary }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, categoryColor.copy(alpha = 0.5f)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Indikator Titik
                Box(modifier = Modifier.size(8.dp).background(categoryColor, CircleShape))
                Spacer(modifier = Modifier.width(12.dp))
                // Judul Event (PERBAIKAN: Berwarna hitam kontras di Light Mode)
                Text(
                    text = event.title,
                    color = MaterialTheme.colorScheme.onSurface, // Dinamis
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            // Waktu Event
            Box(
                modifier = Modifier
                    // PERBAIKAN: Latar belakang wadah jam abu transparan dinamis penjamin kontras di Light Mode
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = event.time,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), // Dinamis abu kontras
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}