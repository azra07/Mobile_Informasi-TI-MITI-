//package com.putrinadya.miti.presentation.screens.student.dashboard
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material.icons.filled.DateRange
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.NavigationBarItemDefaults
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import androidx.compose.ui.window.DialogProperties
//import com.putrinadya.miti.domain.model.Event
//import com.putrinadya.miti.presentation.screens.student.calendar.Calender
//import com.putrinadya.miti.presentation.screens.profile.ProfileMahasiswa
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MahasiswaDashboard(viewModel: MahasiswaViewModel) {
//    val backgroundColor = Color(0xFF030A16)
//    val primaryCyan = Color(0xFF00E5FF)
//
//    Scaffold(
//        bottomBar = {
//            NavigationBar(
//                containerColor = Color(0xFF040F1F),
//                tonalElevation = 8.dp
//            ) {
//                NavigationBarItem(
//                    selected = viewModel.selectedTab == 0,
//                    onClick = { viewModel.onTabSelected(0) },
//                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
//                    label = { Text("Home") },
//                    colors = NavigationBarItemDefaults.colors(
//                        selectedIconColor = backgroundColor,
//                        selectedTextColor = primaryCyan,
//                        indicatorColor = primaryCyan,
//                        unselectedIconColor = Color.Companion.Gray,
//                        unselectedTextColor = Color.Companion.Gray
//                    )
//                )
//                NavigationBarItem(
//                    selected = viewModel.selectedTab == 1,
//                    onClick = { viewModel.onTabSelected(1) },
//                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Calendar") },
//                    label = { Text("Calendar") },
//                    colors = NavigationBarItemDefaults.colors(
//                        selectedIconColor = backgroundColor,
//                        selectedTextColor = primaryCyan,
//                        indicatorColor = primaryCyan,
//                        unselectedIconColor = Color.Companion.Gray,
//                        unselectedTextColor = Color.Companion.Gray
//                    )
//                )
//                NavigationBarItem(
//                    selected = viewModel.selectedTab == 2,
//                    onClick = { viewModel.onTabSelected(2) },
//                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
//                    label = { Text("Profile") },
//                    colors = NavigationBarItemDefaults.colors(
//                        selectedIconColor = backgroundColor,
//                        selectedTextColor = primaryCyan,
//                        indicatorColor = primaryCyan,
//                        unselectedIconColor = Color.Companion.Gray,
//                        unselectedTextColor = Color.Companion.Gray
//                    )
//                )
//            }
//        }
//    ) { innerPadding ->
//        Box(
//            modifier = Modifier.Companion
//                .fillMaxSize()
//                .background(backgroundColor)
//                .padding(innerPadding)
//        ) {
//            when (viewModel.selectedTab) {
//                0 -> {
//                    // TAB HOME / DASHBOARD
//                    LazyColumn(
//                        modifier = Modifier.Companion
//                            .fillMaxSize()
//                            .padding(horizontal = 16.dp),
//                        verticalArrangement = Arrangement.spacedBy(16.dp)
//                    ) {
//                        item {
//                            Row(
//                                modifier = Modifier.Companion.fillMaxWidth().padding(top = 16.dp),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.Companion.CenterVertically
//                            ) {
//                                Column {
//                                    Text(
//                                        "MITI",
//                                        fontSize = 20.sp,
//                                        fontWeight = FontWeight.Companion.Bold,
//                                        color = Color.Companion.White
//                                    )
//                                    Spacer(modifier = Modifier.Companion.height(4.dp))
//                                    Text(
//                                        "Good Evening,",
//                                        fontSize = 14.sp,
//                                        color = Color.Companion.Gray
//                                    )
//                                    Text(
//                                        "Alex!",
//                                        fontSize = 22.sp,
//                                        fontWeight = FontWeight.Companion.Bold,
//                                        color = Color.Companion.White
//                                    )
//                                }
//                                Box(
//                                    modifier = Modifier.Companion.size(45.dp)
//                                        .background(primaryCyan, shape = CircleShape),
//                                    contentAlignment = Alignment.Companion.Center
//                                ) {
//                                    Text(
//                                        "AJ",
//                                        fontWeight = FontWeight.Companion.Bold,
//                                        color = backgroundColor,
//                                        fontSize = 16.sp
//                                    )
//                                }
//                            }
//                        }
//
//                        item {
//                            OutlinedTextField(
//                                value = viewModel.searchQuery,
//                                onValueChange = { viewModel.onSearchQueryChanged(it) },
//                                placeholder = {
//                                    Text(
//                                        "Search events, workshops...",
//                                        color = Color.Companion.Gray
//                                    )
//                                },
//                                leadingIcon = {
//                                    Icon(
//                                        Icons.Default.Search,
//                                        contentDescription = "Search",
//                                        tint = Color.Companion.Gray
//                                    )
//                                },
//                                modifier = Modifier.Companion.fillMaxWidth(),
//                                shape = RoundedCornerShape(12.dp),
//                                colors = OutlinedTextFieldDefaults.colors(
//                                    focusedTextColor = Color.Companion.White,
//                                    unfocusedTextColor = Color.Companion.White,
//                                    focusedContainerColor = Color(0xFF091522),
//                                    unfocusedContainerColor = Color(0xFF091522),
//                                    focusedBorderColor = primaryCyan,
//                                    unfocusedBorderColor = Color.Companion.Transparent
//                                )
//                            )
//                        }
//
//                        item {
//                            Column {
//                                Row(
//                                    modifier = Modifier.Companion.fillMaxWidth(),
//                                    horizontalArrangement = Arrangement.SpaceBetween,
//                                    verticalAlignment = Alignment.Companion.CenterVertically
//                                ) {
//                                    Text(
//                                        "Featured Tech Events",
//                                        fontSize = 18.sp,
//                                        fontWeight = FontWeight.Companion.Bold,
//                                        color = Color.Companion.White
//                                    )
//                                    Text("See all", fontSize = 14.sp, color = primaryCyan)
//                                }
//                                Spacer(modifier = Modifier.Companion.height(12.dp))
//                                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                                    items(viewModel.allEvents.take(3)) { event ->
//                                        FeaturedEventCard(
//                                            event = event,
//                                            onClick = { viewModel.onEventSelected(event) }
//                                        )
//                                    }
//                                }
//                            }
//                        }
//
//                        item {
//                            Row(
//                                modifier = Modifier.Companion.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.Companion.CenterVertically
//                            ) {
//                                Text(
//                                    "Upcoming Activities",
//                                    fontSize = 18.sp,
//                                    fontWeight = FontWeight.Companion.Bold,
//                                    color = Color.Companion.White
//                                )
//                                Text("View all", fontSize = 14.sp, color = primaryCyan)
//                            }
//                        }
//
//                        items(viewModel.filteredEvents) { event ->
//                            UpcomingActivityCard(
//                                event = event,
//                                onClick = { viewModel.onEventSelected(event) }
//                            )
//                        }
//                    }
//                }
//
//                1 -> {
//                    // TAB CALENDAR
//                    Calender()
//                }
//
//                2 -> {
//                    // TAB PROFILE
//                    ProfileMahasiswa(viewModel = viewModel)
//                }
//            }
//
//            // POP-UP PENDAFTARAN (DIALOG)
//            viewModel.selectedEventForRegistration?.let { event ->
//                val isJoined = viewModel.registeredEvents.any { it.title == event.title }
//
//                RegistrationPopUp(
//                    event = event,
//                    isJoined = isJoined,
//                    onClose = { viewModel.onEventSelected(null) },
//                    onJoinClick = {
//                        if (isJoined) {
//                            viewModel.unregisterForEvent(event)
//                        } else {
//                            viewModel.registerForEvent(event)
//                        }
//                    }
//                )
//            }
//        }
//    }
//}
//
//// ================= COMPONENT PEMBANTU: FEATURED CARD =================
//@Composable
//fun FeaturedEventCard(event: Event, onClick: () -> Unit) {
//    Box(
//        modifier = Modifier.Companion
//            .width(260.dp)
//            .height(180.dp)
//            .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
//            .background(Color(0xFF00E5FF))
//            .clickable { onClick() }
//            .padding(16.dp)
//    ) {
//        Column(
//            verticalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.Companion.fillMaxSize()
//        ) {
//            Column {
//                Text(
//                    text = event.category.uppercase(),
//                    fontSize = 11.sp,
//                    color = Color(0xFF030A16),
//                    fontWeight = FontWeight.Companion.Bold,
//                    modifier = Modifier.Companion
//                        .background(
//                            Color.Companion.White.copy(alpha = 0.3f),
//                            androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
//                        )
//                        .padding(horizontal = 6.dp, vertical = 2.dp)
//                )
//                Spacer(modifier = Modifier.Companion.height(8.dp))
//                Text(
//                    text = event.title,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Companion.Bold,
//                    color = Color(0xFF030A16),
//                    maxLines = 1,
//                    overflow = TextOverflow.Companion.Ellipsis
//                )
//                Spacer(modifier = Modifier.Companion.height(4.dp))
//                Text(
//                    text = event.description,
//                    fontSize = 12.sp,
//                    color = Color(0xFF030A16).copy(alpha = 0.8f),
//                    maxLines = 2,
//                    overflow = TextOverflow.Companion.Ellipsis
//                )
//            }
//
//            Row(
//                modifier = Modifier.Companion.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.Companion.CenterVertically
//            ) {
//                Text(
//                    event.time,
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Companion.Bold,
//                    color = Color(0xFF030A16)
//                )
//                Text(
//                    "👥 ${event.currentParticipants}/${event.maxParticipants}",
//                    fontSize = 12.sp,
//                    color = Color(0xFF030A16)
//                )
//            }
//        }
//    }
//}
//
//// ================= COMPONENT PEMBANTU: UPCOMING CARD =================
//@Composable
//fun UpcomingActivityCard(event: Event, onClick: () -> Unit) {
//    Card(
//        modifier = Modifier.Companion
//            .fillMaxWidth()
//            .clickable { onClick() }
//            .padding(vertical = 4.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFF091522)),
//        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
//    ) {
//        Row(
//            modifier = Modifier.Companion
//                .fillMaxWidth()
//                .padding(12.dp),
//            verticalAlignment = Alignment.Companion.CenterVertically
//        ) {
//            Box(
//                modifier = Modifier.Companion
//                    .size(52.dp)
//                    .background(
//                        Color(0xFF142233),
//                        androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
//                    ),
//                contentAlignment = Alignment.Companion.Center
//            ) {
//                Column(horizontalAlignment = Alignment.Companion.CenterHorizontally) {
//                    Text(event.dateMonth, fontSize = 11.sp, color = Color.Companion.Gray)
//                    Text(
//                        event.dateDay,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Companion.Bold,
//                        color = Color.Companion.White
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.Companion.width(12.dp))
//
//            Column(modifier = Modifier.Companion.weight(1f)) {
//                Text(
//                    event.title,
//                    fontSize = 15.sp,
//                    fontWeight = FontWeight.Companion.Bold,
//                    color = Color.Companion.White
//                )
//                Spacer(modifier = Modifier.Companion.height(4.dp))
//                Row(verticalAlignment = Alignment.Companion.CenterVertically) {
//                    Text(event.time, fontSize = 11.sp, color = Color.Companion.Gray)
//                    Text(" • ", fontSize = 11.sp, color = Color.Companion.Gray)
//                    Text(
//                        event.location,
//                        fontSize = 11.sp,
//                        color = Color.Companion.Gray,
//                        maxLines = 1,
//                        overflow = TextOverflow.Companion.Ellipsis
//                    )
//                }
//            }
//
//            Box(
//                modifier = Modifier.Companion
//                    .background(
//                        event.categoryColor.copy(alpha = 0.2f),
//                        androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
//                    )
//                    .padding(horizontal = 8.dp, vertical = 4.dp)
//            ) {
//                Text(
//                    text = event.category,
//                    fontSize = 11.sp,
//                    color = event.categoryColor,
//                    fontWeight = FontWeight.Companion.Bold
//                )
//            }
//        }
//    }
//}
//
//// ================= COMPONENT PEMBANTU: POP-UP DETAIL EVENT =================
//@Composable
//fun RegistrationPopUp(
//    event: Event,
//    isJoined: Boolean,
//    onClose: () -> Unit,
//    onJoinClick: () -> Unit
//) {
//    Dialog(
//        onDismissRequest = onClose,
//        properties = DialogProperties(usePlatformDefaultWidth = false)
//    ) {
//        Card(
//            modifier = Modifier.Companion
//                .fillMaxWidth()
//                .padding(24.dp)
//                .wrapContentHeight(),
//            colors = CardDefaults.cardColors(containerColor = Color(0xFF030A16)),
//            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
//        ) {
//            Column(modifier = Modifier.Companion.fillMaxWidth()) {
//                Box(
//                    modifier = Modifier.Companion
//                        .fillMaxWidth()
//                        .background(Color(0xFF098A5C))
//                        .padding(20.dp)
//                ) {
//                    Column(modifier = Modifier.Companion.fillMaxWidth()) {
//                        Row(
//                            modifier = Modifier.Companion.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.Companion.CenterVertically
//                        ) {
//                            Text(
//                                text = event.category,
//                                color = Color.Companion.White.copy(alpha = 0.8f),
//                                fontSize = 12.sp,
//                                fontWeight = FontWeight.Companion.Bold,
//                                modifier = Modifier.Companion
//                                    .background(
//                                        Color.Companion.White.copy(alpha = 0.2f),
//                                        androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
//                                    )
//                                    .padding(horizontal = 8.dp, vertical = 4.dp)
//                            )
//                            Box(
//                                modifier = Modifier.Companion
//                                    .size(32.dp)
//                                    .background(
//                                        Color.Companion.White.copy(alpha = 0.2f),
//                                        CircleShape
//                                    )
//                                    .clickable { onClose() },
//                                contentAlignment = Alignment.Companion.Center
//                            ) {
//                                Icon(
//                                    Icons.Default.Close,
//                                    contentDescription = "Close",
//                                    tint = Color.Companion.White,
//                                    modifier = Modifier.Companion.size(18.dp)
//                                )
//                            }
//                        }
//                        Spacer(modifier = Modifier.Companion.height(16.dp))
//                        Text(
//                            text = event.title,
//                            color = Color.Companion.White,
//                            fontSize = 22.sp,
//                            fontWeight = FontWeight.Companion.Bold
//                        )
//                    }
//                }
//
//                Column(
//                    modifier = Modifier.Companion
//                        .fillMaxWidth()
//                        .padding(20.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    Row(
//                        modifier = Modifier.Companion.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(12.dp)
//                    ) {
//                        RowInfoBox(
//                            title = "DATE",
//                            value = "${event.dateMonth} ${event.dateDay}, 2026",
//                            icon = "📅",
//                            modifier = Modifier.Companion.weight(1f)
//                        )
//                        RowInfoBox(
//                            title = "TIME",
//                            value = event.time,
//                            icon = "🕒",
//                            modifier = Modifier.Companion.weight(1f)
//                        )
//                    }
//
//                    RowInfoBox(
//                        title = "LOCATION",
//                        value = event.location,
//                        icon = "📍",
//                        modifier = Modifier.Companion.fillMaxWidth()
//                    )
//
//                    Box(
//                        modifier = Modifier.Companion
//                            .fillMaxWidth()
//                            .border(
//                                1.dp,
//                                Color(0xFF003D4C),
//                                androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
//                            )
//                            .background(Color(0xFF061825))
//                            .padding(14.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier.Companion.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.Companion.CenterVertically
//                        ) {
//                            Row(verticalAlignment = Alignment.Companion.CenterVertically) {
//                                Text("👥", fontSize = 16.sp)
//                                Spacer(modifier = Modifier.Companion.width(8.dp))
//                                Text(
//                                    "Available Spots",
//                                    color = Color.Companion.White,
//                                    fontSize = 14.sp
//                                )
//                            }
//                            Text(
//                                "8 / 50",
//                                color = Color(0xFF00E5FF),
//                                fontWeight = FontWeight.Companion.Bold,
//                                fontSize = 14.sp
//                            )
//                        }
//                    }
//
//                    Text(
//                        "Description",
//                        color = Color.Companion.White,
//                        fontWeight = FontWeight.Companion.Bold,
//                        fontSize = 14.sp
//                    )
//                    Text(
//                        text = "Learn the fundamentals of user interface and experience design with hands-on projects.",
//                        color = Color.Companion.Gray,
//                        fontSize = 13.sp,
//                        lineHeight = 18.sp
//                    )
//
//                    Spacer(modifier = Modifier.Companion.height(8.dp))
//
//                    Button(
//                        onClick = onJoinClick,
//                        modifier = Modifier.Companion
//                            .fillMaxWidth()
//                            .height(50.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = if (isJoined) Color(0xFF00E676) else Color.Companion.White
//                        ),
//                        shape = androidx.compose.foundation.shape.RoundedCornerShape(25.dp)
//                    ) {
//                        Text(
//                            text = if (isJoined) "Joined / Registered ✓" else "Join Activity",
//                            color = if (isJoined) Color.Companion.White else Color.Companion.Black,
//                            fontWeight = FontWeight.Companion.Bold,
//                            fontSize = 15.sp
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun RowInfoBox(title: String, value: String, icon: String, modifier: Modifier = Modifier.Companion) {
//    Box(
//        modifier = modifier
//            .background(
//                Color(0xFF091522),
//                androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
//            )
//            .padding(14.dp)
//    ) {
//        Row(verticalAlignment = Alignment.Companion.CenterVertically) {
//            Text(icon, fontSize = 16.sp)
//            Spacer(modifier = Modifier.Companion.width(10.dp))
//            Column {
//                Text(title, color = Color.Companion.Gray, fontSize = 10.sp)
//                Text(
//                    value,
//                    color = Color.Companion.White,
//                    fontSize = 13.sp,
//                    fontWeight = FontWeight.Companion.Bold
//                )
//            }
//        }
//    }
//}