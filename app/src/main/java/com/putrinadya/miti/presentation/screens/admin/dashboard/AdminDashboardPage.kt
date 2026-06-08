package com.putrinadya.miti.presentation.screens.admin.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.presentation.screens.profile.ProfileAdmin

@Composable
fun AdminDashboardPage(viewModel: AdminDashboardViewModel) {
    val backgroundColor = Color(0xFF030A16)
    val cardColor = Color(0xFF091522)
    val primaryCyan = Color(0xFF00E5FF)
    val textWhite = Color(0xFFFFFFFF)

    val uiState = viewModel.uiState

    if (uiState.currentSubScreen == "profile") {
        ProfileAdmin(onBackClick = { viewModel.onNavigateToSubScreen("dashboard") })
    } else {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { viewModel.onShowCreateEventDialog(true) },
                    containerColor = primaryCyan,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Event", tint = backgroundColor)
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Admin
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("MITI", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = textWhite)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(8.dp).background(Color.Green, CircleShape))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Admin Mode", fontSize = 12.sp, color = Color.Gray)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Good Evening,", fontSize = 14.sp, color = Color.Gray)
                            Text("Dr.!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = textWhite)
                        }

                        // Avatar DSC (Bisa diklik untuk berpindah ke halaman Profil Admin)
                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .background(primaryCyan, shape = CircleShape)
                                .clickable { viewModel.onNavigateToSubScreen("profile") },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("DSC", fontWeight = FontWeight.Bold, color = backgroundColor, fontSize = 15.sp)
                        }
                    }
                }

                // Statistik Row (3 Card)
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        StatCard(title = "Total Events", value = uiState.totalEvents.toString(), iconText = "📅", modifier = Modifier.weight(1f))
                        StatCard(title = "Active Participants", value = uiState.activeParticipants.toString(), iconText = "👥", modifier = Modifier.weight(1.1f))
                        StatCard(title = "New Aspirations", value = uiState.newAspirations.toString(), iconText = "💡", modifier = Modifier.weight(1f))
                    }
                }

                // Manage Events Header
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Manage Events", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textWhite)
                        Box(
                            modifier = Modifier
                                .background(Color(0xFF142233), RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("${viewModel.eventsList.size} events", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                }

                // Daftar Event (Dinamis dari ViewModel)
                items(viewModel.eventsList) { event ->
                    AdminEventCard(event = event)
                }
            }

            // POP-UP FORM DIALOG: CREATE NEW EVENT
            if (uiState.showCreateEventDialog) {
                CreateNewEventDialog(
                    onClose = { viewModel.onShowCreateEventDialog(false) },
                    onSave = { newEvent ->
                        viewModel.createNewEvent(newEvent)
                        viewModel.onShowCreateEventDialog(false)
                    }
                )
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, iconText: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF091522)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(28.dp).background(Color(0xFF142233), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(iconText, fontSize = 14.sp)
            }
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(title, fontSize = 9.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun AdminEventCard(event: Event) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF091522)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier.width(4.dp).fillMaxHeight().height(95.dp).background(event.categoryColor)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(event.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(event.categoryColor.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(event.category, fontSize = 10.sp, color = event.categoryColor, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("👥 ${event.currentParticipants}/${event.maxParticipants}", fontSize = 11.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🕒 ${event.dateMonth} ${event.dateDay} at ${event.time}", fontSize = 11.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("📍 ${event.location}", fontSize = 11.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = Color.Gray)
                }
            }
        }
    }
}

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

    val backgroundColor = Color(0xFF030A16)
    val cardColor = Color(0xFF091522)
    val primaryCyan = Color(0xFF00E5FF)
    val textWhite = Color(0xFFFFFFFF)

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
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Event Title", color = textWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = eventTitle,
                        onValueChange = { eventTitle = it },
                        placeholder = { Text("e.g., UI/UX Design Workshop", color = Color.Gray) },
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
                        placeholder = { Text("Describe the event..", color = Color.Gray) },
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
                            placeholder = { Text("mm/dd/yyyy", color = Color.Gray) },
                            trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = "Calendar", tint = Color.Gray) },
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
                        Text("Time", color = textWhite, fontSize = 12.sp)
                        OutlinedTextField(
                            value = timeString,
                            onValueChange = { timeString = it },
                            placeholder = { Text("e.g., 10:00 AM", color = Color.Gray) },
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
                        placeholder = { Text("e.g., Tech Hub, Room 301", color = Color.Gray) },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = Color.Gray) },
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
                            trailingIcon = { Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Dropdown", tint = Color.Gray) },
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
                            dateMonth = "Apr",
                            dateDay = "30",
                            time = timeString,
                            location = location,
                            currentParticipants = 0,
                            maxParticipants = capacity.toIntOrNull() ?: 50,
                            description = description,
                            categoryColor = Color(0xFF00E676)
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