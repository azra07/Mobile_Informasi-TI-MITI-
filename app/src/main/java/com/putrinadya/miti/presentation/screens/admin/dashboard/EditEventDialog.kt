package com.putrinadya.miti.presentation.screens.admin.dashboard

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.ui.theme.*
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEventDialog(
    event: Event,
    onClose: () -> Unit,
    onSave: (Event) -> Unit
) {
    var eventTitle by remember { mutableStateOf(event.title) }
    var description by remember { mutableStateOf(event.description) }
    var dateString by remember { mutableStateOf(event.fullDate) }
    var timeString by remember { mutableStateOf(event.time) }
    var location by remember { mutableStateOf(event.location) }
    var eventType by remember { mutableStateOf(event.category) }
    var capacity by remember { mutableStateOf(event.maxParticipants.toString()) }

    var dropdownExpanded by remember { mutableStateOf(false) } // State kontrol buka/tutup dropdown
    val eventTypesList = listOf("Workshop", "Hackathon", "Seminar", "Webinar", "Competition", "Fun Match")

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Inisialisasi DatePickerDialog bawaan Android
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                dateString = "${month + 1}/$dayOfMonth/$year"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
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
                    .padding(20.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header Dialog
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Edit Kegiatan", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f), CircleShape)
                            .clickable { onClose() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup", tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(16.dp))
                    }
                }

                // 1. Judul Kegiatan
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Judul Kegiatan", color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp)
                    OutlinedTextField(
                        value = eventTitle,
                        onValueChange = { eventTitle = it },
                        placeholder = { Text("Contoh: Workshop Desain UI/UX", color = MaterialTheme.colorScheme.onBackground) },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                            focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                // 2. Deskripsi
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Deskripsi", color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp)
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Jelaskan detail acara..", color = MaterialTheme.colorScheme.onBackground) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 4,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                            focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                // 3. Tanggal & Waktu (Row)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Tanggal dengan Interaksi Kalender Visual (DatePicker)
                    Column(modifier = Modifier.weight(1.1f)) {
                        Text("Tanggal", color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { datePickerDialog.show() }
                        ) {
                            OutlinedTextField(
                                value = dateString,
                                onValueChange = {},
                                readOnly = true,
                                enabled = false,
                                placeholder = { Text("Pilih Tanggal", color = MaterialTheme.colorScheme.onBackground) },
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.DateRange,
                                        contentDescription = "Kalender",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.clickable { datePickerDialog.show() }
                                    )
                                },
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledTextColor = MaterialTheme.colorScheme.onBackground,
                                    disabledContainerColor = MaterialTheme.colorScheme.onBackground,
                                    disabledBorderColor = MaterialTheme.colorScheme.onBackground,
                                    disabledPlaceholderColor = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                    }

                    // Waktu
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Waktu", color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp)
                        OutlinedTextField(
                            value = timeString,
                            onValueChange = { timeString = it },
                            placeholder = { Text("Contoh: 11:00 WITA", color = MaterialTheme.colorScheme.onBackground) },
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                                focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )
                    }
                }

                // 4. Lokasi
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Lokasi", color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp)
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        placeholder = { Text("Contoh: Aula TI, Gedung B", color = MaterialTheme.colorScheme.onBackground) },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = "Lokasi", tint = MaterialTheme.colorScheme.onBackground) },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                            focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                            focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                // 5. Jenis Kegiatan (Dropdown) & Kapasitas (Row)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Jenis Kegiatan dengan Dropdown Menu (Tanpa ketik manual)
                    Column(modifier = Modifier.weight(1.2f)) {
                        Text("Jenis Kegiatan", color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { dropdownExpanded = true } // Klik box untuk memicu Dropdown Menu
                        ) {
                            OutlinedTextField(
                                value = eventType,
                                onValueChange = {},
                                readOnly = true, // Mengunci input agar tidak memicu keyboard virtual
                                enabled = false,
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.KeyboardArrowDown,
                                        contentDescription = "Dropdown",
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.clickable { dropdownExpanded = true }
                                    )
                                },
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledTextColor = MaterialTheme.colorScheme.onBackground,
                                    disabledContainerColor = MaterialTheme.colorScheme.onBackground,
                                    disabledBorderColor = MaterialTheme.colorScheme.onBackground,
                                    disabledPlaceholderColor = MaterialTheme.colorScheme.onBackground
                                )
                            )

                            // Tampilan list Dropdown Menu Vertikal
                            DropdownMenu(
                                expanded = dropdownExpanded,
                                onDismissRequest = { dropdownExpanded = false },
                                modifier = Modifier.fillMaxWidth(0.5f).background(MaterialTheme.colorScheme.onBackground)
                            ) {
                                eventTypesList.forEach { type ->
                                    DropdownMenuItem(
                                        text = { Text(type, color = MaterialTheme.colorScheme.onBackground) },
                                        onClick = {
                                            eventType = type // Ubah jenis kegiatan sesuai yang dipilih
                                            dropdownExpanded = false // Tutup menu dropdown
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Kapasitas
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Kapasitas", color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp)
                        OutlinedTextField(
                            value = capacity,
                            onValueChange = { capacity = it },
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                                focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Tombol Simpan Perubahan
                Button(
                    onClick = {
                        val dateParts = dateString.split("/")
                        val parsedMonth = if (dateParts.isNotEmpty()) {
                            when (dateParts[0].toIntOrNull()) {
                                1 -> "Jan"; 2 -> "Feb"; 3 -> "Mar"; 4 -> "Apr"; 5 -> "May"; 6 -> "Jun"
                                7 -> "Jul"; 8 -> "Aug"; 9 -> "Sep"; 10 -> "Oct"; 11 -> "Nov"; 12 -> "Dec"
                                else -> "Event"
                            }
                        } else "Event"

                        val parsedDay = if (dateParts.size >= 2) dateParts[1] else "1"

                        // Integrasi penyesuaian Hex warna berdasarkan tipe event baru
                        val colorHex = when (eventType.lowercase()) {
                            "workshop" -> "#C583FF"       // MitiWorkshop
                            "hackathon" -> "#00E676"      // MitiHackathon
                            "seminar" -> "#00B0FF"        // MitiSeminar
                            "webinar" -> "#2979FF"        // MitiWebinar
                            "competition" -> "#FFD600"    // MitiCompetition
                            "fun match" -> "#FF8A80"      // MitiFunMatch
                            else -> "#9E9E9E"             // MitiGray
                        }

                        // Menggabungkan seluruh data baru yang telah diedit
                        val updatedEvent = event.copy(
                            title = eventTitle,
                            category = eventType,
                            dayMonth = parsedMonth,
                            year = parsedDay,
                            fullDate = dateString,
                            time = timeString,
                            location = location,
                            maxParticipants = capacity.toIntOrNull() ?: 50,
                            description = description,
                            categoryColorHex = colorHex
                        )
                        onSave(updatedEvent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Simpan Perubahan", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}