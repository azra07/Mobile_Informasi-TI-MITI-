package com.putrinadya.miti.presentation.screens.admin.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEventDialog(
    event: Event,
    onClose: () -> Unit,
    onSave: (Event) -> Unit
) {
    // Inisialisasi state dengan data lama dari parameter 'event'
    var eventTitle by remember { mutableStateOf(event.title) }
    var description by remember { mutableStateOf(event.description) }
    var dateString by remember { mutableStateOf(event.fullDate) }
    var timeString by remember { mutableStateOf(event.time) }
    var location by remember { mutableStateOf(event.location) }
    var eventType by remember { mutableStateOf(event.category) }
    var capacity by remember { mutableStateOf(event.maxParticipants.toString()) }

    val cardColor = MitiCard
    val primaryCyan = MitiCyan
    val textWhite = MitiWhite

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(24.dp).wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF05111E)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Edit Event", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textWhite)
                    Box(
                        modifier = Modifier.size(32.dp).background(Color.White.copy(alpha = 0.1f), CircleShape).clickable { onClose() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = textWhite, modifier = Modifier.size(16.dp))
                    }
                }

                // Judul
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Event Title", color = textWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = eventTitle,
                        onValueChange = { eventTitle = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textWhite,
                            unfocusedTextColor = textWhite,
                            focusedContainerColor = cardColor,
                            unfocusedContainerColor = cardColor,
                            focusedBorderColor = primaryCyan,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                // Deskripsi
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Description", color = textWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier.fillMaxWidth().height(100.dp).padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textWhite,
                            unfocusedTextColor = textWhite,
                            focusedContainerColor = cardColor,
                            unfocusedContainerColor = cardColor,
                            focusedBorderColor = primaryCyan,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                // Date & Time Row
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column(modifier = Modifier.weight(1.1f)) {
                        Text("Date", color = textWhite, fontSize = 12.sp)
                        OutlinedTextField(
                            value = dateString,
                            onValueChange = { dateString = it },
                            trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = null, tint = MitiGray) },
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = cardColor, unfocusedContainerColor = cardColor, unfocusedBorderColor = Color.Transparent)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Time", color = textWhite, fontSize = 12.sp)
                        OutlinedTextField(
                            value = timeString,
                            onValueChange = { timeString = it },
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = cardColor, unfocusedContainerColor = cardColor, unfocusedBorderColor = Color.Transparent)
                        )
                    }
                }

                // Tombol Simpan
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        val updatedEvent = event.copy(
                            title = eventTitle,
                            category = eventType,
                            fullDate = dateString,
                            time = timeString,
                            location = location,
                            description = description,
                            maxParticipants = capacity.toIntOrNull() ?: event.maxParticipants
                        )
                        onSave(updatedEvent)
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryCyan),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Save Changes", color = MitiNavy, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}