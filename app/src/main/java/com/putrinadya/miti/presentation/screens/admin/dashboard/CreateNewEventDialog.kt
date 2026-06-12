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
fun CreateNewEventDialog(
    onClose: () -> Unit,
    onSave: (Event) -> Unit
) {
    var eventTitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dateString by remember { mutableStateOf("") }
    var timeString by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var eventType by remember { mutableStateOf("Workshop") }
    var capacity by remember { mutableStateOf("50") }

    var dropdownExpanded by remember { mutableStateOf(false) } // State kontrol dropdown jenis event
    val eventTypesList = listOf("Workshop", "Hackathon", "Seminar", "Webinar", "Competition", "Fun Match")

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Inisialisasi DatePickerDialog bawaan Android
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // Menyimpan tanggal terpilih dengan format M/d/yyyy (contoh: 7/13/2026)
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
            colors = CardDefaults.cardColors(containerColor = MitiCard),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(scrollState), // Form dapat di-scroll jika layar HP kecil
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header Dialog
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Buat Kegiatan Baru", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MitiWhite)
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(MitiWhite.copy(alpha = 0.1f), CircleShape)
                            .clickable { onClose() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup", tint = MitiWhite, modifier = Modifier.size(16.dp))
                    }
                }

                // 1. Judul Kegiatan
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Judul Kegiatan", color = MitiWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = eventTitle,
                        onValueChange = { eventTitle = it },
                        placeholder = { Text("Nama Kegiatan", color = MitiGray) },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MitiWhite,
                            unfocusedTextColor = MitiWhite,
                            focusedContainerColor = MitiNavy,
                            unfocusedContainerColor = MitiNavy,
                            focusedBorderColor = MitiCyan,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                // 2. Deskripsi
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Deskripsi", color = MitiWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Jelaskan detail acara..", color = MitiGray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 4,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MitiWhite,
                            unfocusedTextColor = MitiWhite,
                            focusedContainerColor = MitiNavy,
                            unfocusedContainerColor = MitiNavy,
                            focusedBorderColor = MitiCyan,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                // 3. Tanggal & Waktu (Row)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Tanggal dengan Kalender Visual (DatePicker)
                    Column(modifier = Modifier.weight(1.1f)) {
                        Text("Tanggal", color = MitiWhite, fontSize = 12.sp)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { datePickerDialog.show() } // Klik kolom untuk membuka kalender
                        ) {
                            OutlinedTextField(
                                value = dateString,
                                onValueChange = {},
                                readOnly = true, // Dikunci agar tidak diketik manual
                                enabled = false, // Menghindari soft keyboard muncul
                                placeholder = { Text("Pilih Tanggal", color = MitiGray) },
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.DateRange,
                                        contentDescription = "Kalender",
                                        tint = MitiCyan,
                                        modifier = Modifier.clickable { datePickerDialog.show() }
                                    )
                                },
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledTextColor = MitiWhite,
                                    disabledContainerColor = MitiNavy,
                                    disabledBorderColor = MitiCyan,
                                    disabledPlaceholderColor = MitiGray
                                )
                            )
                        }
                    }

                    // Waktu
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Waktu", color = MitiWhite, fontSize = 12.sp)
                        OutlinedTextField(
                            value = timeString,
                            onValueChange = { timeString = it },
                            placeholder = { Text("00:00 WITA", color = MitiGray) },
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MitiWhite,
                                unfocusedTextColor = MitiWhite,
                                focusedContainerColor = MitiNavy,
                                unfocusedContainerColor = MitiNavy,
                                focusedBorderColor = MitiCyan,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )
                    }
                }

                // 4. Lokasi
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Lokasi", color = MitiWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        placeholder = { Text("Ruang A-14 FT ULM BJM", color = MitiGray) },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = "Lokasi", tint = MitiGray) },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MitiWhite,
                            unfocusedTextColor = MitiWhite,
                            focusedContainerColor = MitiNavy,
                            unfocusedContainerColor = MitiNavy,
                            focusedBorderColor = MitiCyan,
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
                        Text("Jenis Kegiatan", color = MitiWhite, fontSize = 12.sp)
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
                                        tint = MitiCyan,
                                        modifier = Modifier.clickable { dropdownExpanded = true }
                                    )
                                },
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledTextColor = MitiWhite,
                                    disabledContainerColor = MitiNavy,
                                    disabledBorderColor = MitiCyan,
                                    disabledPlaceholderColor = MitiGray
                                )
                            )

                            // Tampilan list Dropdown Menu Vertikal
                            DropdownMenu(
                                expanded = dropdownExpanded,
                                onDismissRequest = { dropdownExpanded = false },
                                modifier = Modifier.fillMaxWidth(0.5f).background(MitiCard)
                            ) {
                                eventTypesList.forEach { type ->
                                    DropdownMenuItem(
                                        text = { Text(type, color = MitiWhite) },
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
                        Text("Kapasitas", color = MitiWhite, fontSize = 12.sp)
                        OutlinedTextField(
                            value = capacity,
                            onValueChange = { capacity = it },
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = MitiWhite,
                                unfocusedTextColor = MitiWhite,
                                focusedContainerColor = MitiNavy,
                                unfocusedContainerColor = MitiNavy,
                                focusedBorderColor = MitiCyan,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Tombol Buat Kegiatan
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

                        val newEvent = Event(
                            title = eventTitle,
                            category = eventType,
                            dayMonth = parsedMonth,
                            year = parsedDay,
                            fullDate = dateString,
                            time = timeString,
                            location = location,
                            currentParticipants = 0,
                            maxParticipants = capacity.toIntOrNull() ?: 50,
                            description = description,
                            categoryColorHex = colorHex
                        )
                        onSave(newEvent)
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MitiCyan),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Buat Kegiatan", color = MitiNavy, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}