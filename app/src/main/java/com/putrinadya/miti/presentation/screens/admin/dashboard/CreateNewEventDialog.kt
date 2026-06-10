package com.putrinadya.miti.presentation.screens.admin.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.ui.theme.*

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

    val backgroundColor = MitiNavy
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Create New Event", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textWhite)
                    Box(
                        modifier = Modifier.size(32.dp).background(Color.White.copy(alpha = 0.1f), CircleShape).clickable { onClose() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = textWhite, modifier = Modifier.size(16.dp))
                    }
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Event Title", color = textWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = eventTitle,
                        onValueChange = { eventTitle = it },
                        placeholder = { Text("e.g., UI/UX Design Workshop", color = MitiGray) },
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

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Description", color = textWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Describe the event..", color = MitiGray) },
                        modifier = Modifier.fillMaxWidth().height(100.dp).padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 4,
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1.1f)) {
                        Text("Date", color = textWhite, fontSize = 12.sp)
                        OutlinedTextField(
                            value = dateString,
                            onValueChange = { dateString = it },
                            placeholder = { Text("mm/dd/yyyy", color = MitiGray) },
                            trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = "Calendar", tint = MitiGray) },
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

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Time", color = MitiWhite, fontSize = 12.sp)
                        OutlinedTextField(
                            value = timeString,
                            onValueChange = { timeString = it },
                            placeholder = { Text("e.g., 10:00 AM", color = MitiGray) },
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
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Location", color = textWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        placeholder = { Text("e.g., Tech Hub, Room 301", color = MitiGray) },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = MitiGray) },
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1.2f)) {
                        Text("Event Type", color = textWhite, fontSize = 12.sp)
                        OutlinedTextField(
                            value = eventType,
                            onValueChange = { eventType = it },
                            trailingIcon = { Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Dropdown", tint = MitiGray) },
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

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Capacity", color = textWhite, fontSize = 12.sp)
                        OutlinedTextField(
                            value = capacity,
                            onValueChange = { capacity = it },
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
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val newEvent = Event(
                            title = eventTitle,
                            category = eventType,
                            dayMonth = "Apr",
                            year = "30",
                            fullDate = dateString,
                            time = timeString,
                            location = location,
                            currentParticipants = 0,
                            maxParticipants = capacity.toIntOrNull() ?: 50,
                            description = description,
                            categoryColor = MitiHackathon
                        )
                        onSave(newEvent)
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryCyan),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Create Event", color = backgroundColor, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}