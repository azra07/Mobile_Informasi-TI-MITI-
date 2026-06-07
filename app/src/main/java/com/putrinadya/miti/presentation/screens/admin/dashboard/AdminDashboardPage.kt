//package com.putrinadya.miti.presentation.screens.admin.dashboard
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material.icons.filled.DateRange
//import androidx.compose.material.icons.filled.KeyboardArrowDown
//import androidx.compose.material.icons.filled.LocationOn
//import androidx.compose.material.icons.filled.MoreVert
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import androidx.compose.ui.window.DialogProperties
//import com.putrinadya.miti.data.remote.dto.Event
//import com.putrinadya.miti.data.remote.dto.dummyEvents
//import com.putrinadya.miti.presentation.screens.profile.ProfileAdmin
//
//@Composable
//fun AdminDashboard() {
//    var currentSubScreen by remember { mutableStateOf("dashboard") }
//    var showCreateEventDialog by remember { mutableStateOf(false) }
//
//    val backgroundColor = Color(0xFF030A16)
//    val cardColor = Color(0xFF091522)
//    val primaryCyan = Color(0xFF00E5FF)
//    val textWhite = Color(0xFFFFFFFF)
//
//    if (currentSubScreen == "profile") {
//        ProfileAdmin(onBackClick = { currentSubScreen = "dashboard" })
//    } else {
//        Scaffold(
//            floatingActionButton = {
//                FloatingActionButton(
//                    onClick = { showCreateEventDialog = true },
//                    containerColor = primaryCyan,
//                    shape = CircleShape
//                ) {
//                    Icon(
//                        Icons.Default.Add,
//                        contentDescription = "Add Event",
//                        tint = backgroundColor
//                    )
//                }
//            }
//        ) { innerPadding ->
//            LazyColumn(
//                modifier = Modifier.Companion
//                    .fillMaxSize()
//                    .background(backgroundColor)
//                    .padding(innerPadding)
//                    .padding(horizontal = 16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                // Header Admin
//                item {
//                    Row(
//                        modifier = Modifier.Companion
//                            .fillMaxWidth()
//                            .padding(top = 16.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.Companion.CenterVertically
//                    ) {
//                        Column {
//                            Text(
//                                "MITI",
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.Companion.Bold,
//                                color = textWhite
//                            )
//                            Row(verticalAlignment = Alignment.Companion.CenterVertically) {
//                                Box(
//                                    modifier = Modifier.Companion.size(8.dp)
//                                        .background(Color.Companion.Green, CircleShape)
//                                )
//                                Spacer(modifier = Modifier.Companion.width(6.dp))
//                                Text("Admin Mode", fontSize = 12.sp, color = Color.Companion.Gray)
//                            }
//                            Spacer(modifier = Modifier.Companion.height(12.dp))
//                            Text("Good Evening,", fontSize = 14.sp, color = Color.Companion.Gray)
//                            Text(
//                                "Dr.!",
//                                fontSize = 22.sp,
//                                fontWeight = FontWeight.Companion.Bold,
//                                color = textWhite
//                            )
//                        }
//
//                        Box(
//                            modifier = Modifier.Companion
//                                .size(45.dp)
//                                .background(primaryCyan, shape = CircleShape)
//                                .clickable { currentSubScreen = "profile" },
//                            contentAlignment = Alignment.Companion.Center
//                        ) {
//                            Text(
//                                "DSC",
//                                fontWeight = FontWeight.Companion.Bold,
//                                color = backgroundColor,
//                                fontSize = 15.sp
//                            )
//                        }
//                    }
//                }
//
//                // Statistik Row (3 Card)
//                item {
//                    Row(
//                        modifier = Modifier.Companion.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        StatCard(
//                            title = "Total Events",
//                            value = "8",
//                            iconText = "📅",
//                            modifier = Modifier.Companion.weight(1f)
//                        )
//                        StatCard(
//                            title = "Active Participants",
//                            value = "643",
//                            iconText = "👥",
//                            modifier = Modifier.Companion.weight(1.1f)
//                        )
//                        StatCard(
//                            title = "New Aspirations",
//                            value = "12",
//                            iconText = "💡",
//                            modifier = Modifier.Companion.weight(1f)
//                        )
//                    }
//                }
//
//                // Manage Events Header
//                item {
//                    Row(
//                        modifier = Modifier.Companion.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.Companion.CenterVertically
//                    ) {
//                        Text(
//                            "Manage Events",
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Companion.Bold,
//                            color = textWhite
//                        )
//                        Box(
//                            modifier = Modifier.Companion
//                                .background(Color(0xFF142233), RoundedCornerShape(12.dp))
//                                .padding(horizontal = 10.dp, vertical = 4.dp)
//                        ) {
//                            Text("8 events", color = Color.Companion.Gray, fontSize = 12.sp)
//                        }
//                    }
//                }
//
//                // Daftar Event
//                items(dummyEvents) { event ->
//                    AdminEventCard(event = event)
//                }
//            }
//
//            if (showCreateEventDialog) {
//                CreateNewEventDialog(
//                    onClose = { showCreateEventDialog = false },
//                    onSave = {
//                        showCreateEventDialog = false
//                    }
//                )
//            }
//        }
//    }
//}
//
//// ================= COMPONENT PEMBANTU: STAT CARD =================
//@Composable
//fun StatCard(title: String, value: String, iconText: String, modifier: Modifier = Modifier.Companion) {
//    Card(
//        modifier = modifier.height(100.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFF091522)),
//        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
//    ) {
//        Column(
//            modifier = Modifier.Companion
//                .fillMaxSize()
//                .padding(12.dp),
//            verticalArrangement = Arrangement.SpaceBetween,
//            horizontalAlignment = Alignment.Companion.CenterHorizontally
//        ) {
//            Box(
//                modifier = Modifier.Companion
//                    .size(28.dp)
//                    .background(Color(0xFF142233), CircleShape),
//                contentAlignment = Alignment.Companion.Center
//            ) {
//                Text(iconText, fontSize = 14.sp)
//            }
//            Text(
//                value,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Companion.Bold,
//                color = Color.Companion.White
//            )
//            Text(
//                title,
//                fontSize = 9.sp,
//                color = Color.Companion.Gray,
//                maxLines = 1,
//                overflow = TextOverflow.Companion.Ellipsis
//            )
//        }
//    }
//}
//
//// ================= COMPONENT PEMBANTU: EVENT CARD ADMIN =================
//@Composable
//fun AdminEventCard(event: Event) {
//    Card(
//        modifier = Modifier.Companion
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFF091522)),
//        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
//    ) {
//        Row(
//            modifier = Modifier.Companion.fillMaxWidth()
//        ) {
//            Box(
//                modifier = Modifier.Companion
//                    .width(4.dp)
//                    .fillMaxHeight()
//                    .height(95.dp)
//                    .background(event.categoryColor)
//            )
//
//            Row(
//                modifier = Modifier.Companion
//                    .fillMaxWidth()
//                    .padding(12.dp),
//                verticalAlignment = Alignment.Companion.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column(
//                    modifier = Modifier.Companion.weight(1f)
//                ) {
//                    Text(
//                        event.title,
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight.Companion.Bold,
//                        color = Color.Companion.White
//                    )
//                    Spacer(modifier = Modifier.Companion.height(6.dp))
//                    Row(verticalAlignment = Alignment.Companion.CenterVertically) {
//                        Box(
//                            modifier = Modifier.Companion
//                                .background(
//                                    event.categoryColor.copy(alpha = 0.15f),
//                                    androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
//                                )
//                                .padding(horizontal = 6.dp, vertical = 2.dp)
//                        ) {
//                            Text(
//                                event.category,
//                                fontSize = 10.sp,
//                                color = event.categoryColor,
//                                fontWeight = FontWeight.Companion.Bold
//                            )
//                        }
//                        Spacer(modifier = Modifier.Companion.width(8.dp))
//                        Text(
//                            "👥 ${event.currentParticipants}/${event.maxParticipants}",
//                            fontSize = 11.sp,
//                            color = Color.Companion.Gray
//                        )
//                    }
//                    Spacer(modifier = Modifier.Companion.height(6.dp))
//                    Row(verticalAlignment = Alignment.Companion.CenterVertically) {
//                        Text(
//                            "🕒 ${event.dateMonth} ${event.dateDay} at ${event.time}",
//                            fontSize = 11.sp,
//                            color = Color.Companion.Gray
//                        )
//                        Spacer(modifier = Modifier.Companion.width(8.dp))
//                        Text(
//                            "📍 ${event.location}",
//                            fontSize = 11.sp,
//                            color = Color.Companion.Gray,
//                            maxLines = 1,
//                            overflow = TextOverflow.Companion.Ellipsis
//                        )
//                    }
//                }
//
//                IconButton(onClick = { }) {
//                    Icon(
//                        Icons.Default.MoreVert,
//                        contentDescription = "Options",
//                        tint = Color.Companion.Gray
//                    )
//                }
//            }
//        }
//    }
//}
//
//// ================= DIALOG FORM: CREATE NEW EVENT =================
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CreateNewEventDialog(
//    onClose: () -> Unit,
//    onSave: () -> Unit
//) {
//    var eventTitle by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var dateString by remember { mutableStateOf("") }
//    var timeString by remember { mutableStateOf("") }
//    var location by remember { mutableStateOf("") }
//    var eventType by remember { mutableStateOf("Workshop") }
//    var capacity by remember { mutableStateOf("50") }
//
//    val backgroundColor = Color(0xFF030A16)
//    val cardColor = Color(0xFF091522)
//    val primaryCyan = Color(0xFF00E5FF)
//    val textWhite = Color(0xFFFFFFFF)
//
//    Dialog(
//        onDismissRequest = onClose,
//        properties = DialogProperties(usePlatformDefaultWidth = false)
//    ) {
//        Card(
//            modifier = Modifier.Companion
//                .fillMaxWidth()
//                .padding(24.dp)
//                .wrapContentHeight(),
//            colors = CardDefaults.cardColors(containerColor = Color(0xFF05111E)),
//            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
//        ) {
//            Column(
//                modifier = Modifier.Companion
//                    .fillMaxWidth()
//                    .padding(20.dp),
//                verticalArrangement = Arrangement.spacedBy(14.dp)
//            ) {
//                Row(
//                    modifier = Modifier.Companion.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.Companion.CenterVertically
//                ) {
//                    Text(
//                        "Create New Event",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Companion.Bold,
//                        color = textWhite
//                    )
//                    Box(
//                        modifier = Modifier.Companion
//                            .size(32.dp)
//                            .background(Color.Companion.White.copy(alpha = 0.1f), CircleShape)
//                            .clickable { onClose() },
//                        contentAlignment = Alignment.Companion.Center
//                    ) {
//                        Icon(
//                            Icons.Default.Close,
//                            contentDescription = "Close",
//                            tint = Color.Companion.White,
//                            modifier = Modifier.Companion.size(16.dp)
//                        )
//                    }
//                }
//
//                Column(modifier = Modifier.Companion.fillMaxWidth()) {
//                    Text("Event Title", color = textWhite, fontSize = 12.sp)
//                    OutlinedTextField(
//                        value = eventTitle,
//                        onValueChange = { eventTitle = it },
//                        placeholder = {
//                            Text(
//                                "e.g., UI/UX Design Workshop",
//                                color = Color.Companion.Gray
//                            )
//                        },
//                        modifier = Modifier.Companion.fillMaxWidth().padding(top = 4.dp),
//                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedTextColor = textWhite,
//                            unfocusedTextColor = textWhite,
//                            focusedContainerColor = cardColor,
//                            unfocusedContainerColor = cardColor,
//                            focusedBorderColor = primaryCyan,
//                            unfocusedBorderColor = Color.Companion.Transparent
//                        )
//                    )
//                }
//
//                Column(modifier = Modifier.Companion.fillMaxWidth()) {
//                    Text("Description", color = textWhite, fontSize = 12.sp)
//                    OutlinedTextField(
//                        value = description,
//                        onValueChange = { description = it },
//                        placeholder = {
//                            Text(
//                                "Describe the event..",
//                                color = Color.Companion.Gray
//                            )
//                        },
//                        modifier = Modifier.Companion
//                            .fillMaxWidth()
//                            .height(100.dp)
//                            .padding(top = 4.dp),
//                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
//                        maxLines = 4,
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedTextColor = textWhite,
//                            unfocusedTextColor = textWhite,
//                            focusedContainerColor = cardColor,
//                            unfocusedContainerColor = cardColor,
//                            focusedBorderColor = primaryCyan,
//                            unfocusedBorderColor = Color.Companion.Transparent
//                        )
//                    )
//                }
//
//                Row(
//                    modifier = Modifier.Companion.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    Column(modifier = Modifier.Companion.weight(1.1f)) {
//                        Text("Date", color = textWhite, fontSize = 12.sp)
//                        OutlinedTextField(
//                            value = dateString,
//                            onValueChange = { dateString = it },
//                            placeholder = { Text("mm/dd/yyyy", color = Color.Companion.Gray) },
//                            trailingIcon = {
//                                Icon(
//                                    Icons.Default.DateRange,
//                                    contentDescription = "Calendar",
//                                    tint = Color.Companion.Gray
//                                )
//                            },
//                            modifier = Modifier.Companion.fillMaxWidth().padding(top = 4.dp),
//                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
//                            colors = OutlinedTextFieldDefaults.colors(
//                                focusedTextColor = textWhite,
//                                unfocusedTextColor = textWhite,
//                                focusedContainerColor = cardColor,
//                                unfocusedContainerColor = cardColor,
//                                focusedBorderColor = primaryCyan,
//                                unfocusedBorderColor = Color.Companion.Transparent
//                            )
//                        )
//                    }
//
//                    Column(modifier = Modifier.Companion.weight(1f)) {
//                        Text("Time", color = textWhite, fontSize = 12.sp)
//                        OutlinedTextField(
//                            value = timeString,
//                            onValueChange = { timeString = it },
//                            placeholder = { Text("e.g., 10:00 AM", color = Color.Companion.Gray) },
//                            modifier = Modifier.Companion.fillMaxWidth().padding(top = 4.dp),
//                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
//                            colors = OutlinedTextFieldDefaults.colors(
//                                focusedTextColor = textWhite,
//                                unfocusedTextColor = textWhite,
//                                focusedContainerColor = cardColor,
//                                unfocusedContainerColor = cardColor,
//                                focusedBorderColor = primaryCyan,
//                                unfocusedBorderColor = Color.Companion.Transparent
//                            )
//                        )
//                    }
//                }
//
//                Column(modifier = Modifier.Companion.fillMaxWidth()) {
//                    Text("Location", color = textWhite, fontSize = 12.sp)
//                    OutlinedTextField(
//                        value = location,
//                        onValueChange = { location = it },
//                        placeholder = {
//                            Text(
//                                "e.g., Tech Hub, Room 301",
//                                color = Color.Companion.Gray
//                            )
//                        },
//                        leadingIcon = {
//                            Icon(
//                                Icons.Default.LocationOn,
//                                contentDescription = "Location",
//                                tint = Color.Companion.Gray
//                            )
//                        },
//                        modifier = Modifier.Companion.fillMaxWidth().padding(top = 4.dp),
//                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedTextColor = textWhite,
//                            unfocusedTextColor = textWhite,
//                            focusedContainerColor = cardColor,
//                            unfocusedContainerColor = cardColor,
//                            focusedBorderColor = primaryCyan,
//                            unfocusedBorderColor = Color.Companion.Transparent
//                        )
//                    )
//                }
//
//                Row(
//                    modifier = Modifier.Companion.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    Column(modifier = Modifier.Companion.weight(1.2f)) {
//                        Text("Event Type", color = textWhite, fontSize = 12.sp)
//                        OutlinedTextField(
//                            value = eventType,
//                            onValueChange = { eventType = it },
//                            trailingIcon = {
//                                Icon(
//                                    Icons.Default.KeyboardArrowDown,
//                                    contentDescription = "Dropdown",
//                                    tint = Color.Companion.Gray
//                                )
//                            },
//                            modifier = Modifier.Companion.fillMaxWidth().padding(top = 4.dp),
//                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
//                            colors = OutlinedTextFieldDefaults.colors(
//                                focusedTextColor = textWhite,
//                                unfocusedTextColor = textWhite,
//                                focusedContainerColor = cardColor,
//                                unfocusedContainerColor = cardColor,
//                                focusedBorderColor = primaryCyan,
//                                unfocusedBorderColor = Color.Companion.Transparent
//                            )
//                        )
//                    }
//
//                    Column(modifier = Modifier.Companion.weight(1f)) {
//                        Text("Capacity", color = textWhite, fontSize = 12.sp)
//                        OutlinedTextField(
//                            value = capacity,
//                            onValueChange = { capacity = it },
//                            modifier = Modifier.Companion.fillMaxWidth().padding(top = 4.dp),
//                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
//                            colors = OutlinedTextFieldDefaults.colors(
//                                focusedTextColor = textWhite,
//                                unfocusedTextColor = textWhite,
//                                focusedContainerColor = cardColor,
//                                unfocusedContainerColor = cardColor,
//                                focusedBorderColor = primaryCyan,
//                                unfocusedBorderColor = Color.Companion.Transparent
//                            )
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.Companion.height(12.dp))
//
//                Button(
//                    onClick = onSave,
//                    modifier = Modifier.Companion
//                        .fillMaxWidth()
//                        .height(50.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = primaryCyan),
//                    shape = androidx.compose.foundation.shape.RoundedCornerShape(25.dp)
//                ) {
//                    Text(
//                        "Create Event",
//                        color = backgroundColor,
//                        fontWeight = FontWeight.Companion.Bold
//                    )
//                }
//            }
//        }
//    }
//}