package com.example.miti.feature.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.miti.feature.domain.model.Event
import com.example.miti.feature.presentation.viewmodel.MahasiswaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MahasiswaDashboard(viewModel: MahasiswaViewModel) {
    val backgroundColor = Color(0xFF030A16)
    val primaryCyan = Color(0xFF00E5FF)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF040F1F),
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = viewModel.selectedTab == 0,
                    onClick = { viewModel.onTabSelected(0) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = backgroundColor,
                        selectedTextColor = primaryCyan,
                        indicatorColor = primaryCyan,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    selected = viewModel.selectedTab == 1,
                    onClick = { viewModel.onTabSelected(1) },
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Calendar") },
                    label = { Text("Calendar") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = backgroundColor,
                        selectedTextColor = primaryCyan,
                        indicatorColor = primaryCyan,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    selected = viewModel.selectedTab == 2,
                    onClick = { viewModel.onTabSelected(2) },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = backgroundColor,
                        selectedTextColor = primaryCyan,
                        indicatorColor = primaryCyan,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding)
        ) {
            when (viewModel.selectedTab) {
                0 -> {
                    // TAB HOME / DASHBOARD
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("MITI", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Good Evening,", fontSize = 14.sp, color = Color.Gray)
                                    Text("Alex!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                                Box(
                                    modifier = Modifier.size(45.dp).background(primaryCyan, shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("AJ", fontWeight = FontWeight.Bold, color = backgroundColor, fontSize = 16.sp)
                                }
                            }
                        }

                        item {
                            OutlinedTextField(
                                value = viewModel.searchQuery,
                                onValueChange = { viewModel.onSearchQueryChanged(it) },
                                placeholder = { Text("Search events, workshops...", color = Color.Gray) },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedContainerColor = Color(0xFF091522),
                                    unfocusedContainerColor = Color(0xFF091522),
                                    focusedBorderColor = primaryCyan,
                                    unfocusedBorderColor = Color.Transparent
                                )
                            )
                        }

                        item {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Featured Tech Events", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    Text("See all", fontSize = 14.sp, color = primaryCyan)
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    items(viewModel.allEvents.take(3)) { event ->
                                        FeaturedEventCard(
                                            event = event,
                                            onClick = { viewModel.onEventSelected(event) }
                                        )
                                    }
                                }
                            }
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Upcoming Activities", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text("View all", fontSize = 14.sp, color = primaryCyan)
                            }
                        }

                        items(viewModel.filteredEvents) { event ->
                            UpcomingActivityCard(
                                event = event,
                                onClick = { viewModel.onEventSelected(event) }
                            )
                        }
                    }
                }
                1 -> {
                    // TAB CALENDAR
                    Calender()
                }
                2 -> {
                    // TAB PROFILE
                    ProfileMahasiswa(viewModel = viewModel)
                }
            }

            // POP-UP PENDAFTARAN (DIALOG)
            viewModel.selectedEventForRegistration?.let { event ->
                val isJoined = viewModel.registeredEvents.any { it.title == event.title }

                RegistrationPopUp(
                    event = event,
                    isJoined = isJoined,
                    onClose = { viewModel.onEventSelected(null) },
                    onJoinClick = {
                        if (isJoined) {
                            viewModel.unregisterForEvent(event)
                        } else {
                            viewModel.registerForEvent(event)
                        }
                    }
                )
            }
        }
    }
}

// ================= COMPONENT PEMBANTU: FEATURED CARD =================
@Composable
fun FeaturedEventCard(event: Event, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(260.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF00E5FF))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Text(
                    text = event.category.uppercase(),
                    fontSize = 11.sp,
                    color = Color(0xFF030A16),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = event.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF030A16),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.description,
                    fontSize = 12.sp,
                    color = Color(0xFF030A16).copy(alpha = 0.8f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(event.time, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF030A16))
                Text("👥 ${event.currentParticipants}/${event.maxParticipants}", fontSize = 12.sp, color = Color(0xFF030A16))
            }
        }
    }
}

// ================= COMPONENT PEMBANTU: UPCOMING CARD =================
@Composable
fun UpcomingActivityCard(event: Event, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF091522)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color(0xFF142233), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(event.dateMonth, fontSize = 11.sp, color = Color.Gray)
                    Text(event.dateDay, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(event.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(event.time, fontSize = 11.sp, color = Color.Gray)
                    Text(" • ", fontSize = 11.sp, color = Color.Gray)
                    Text(event.location, fontSize = 11.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

            Box(
                modifier = Modifier
                    .background(event.categoryColor.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = event.category,
                    fontSize = 11.sp,
                    color = event.categoryColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ================= COMPONENT PEMBANTU: POP-UP DETAIL EVENT =================
@Composable
fun RegistrationPopUp(
    event: Event,
    isJoined: Boolean,
    onClose: () -> Unit,
    onJoinClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF030A16)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF098A5C))
                        .padding(20.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = event.category,
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
                                    .clickable { onClose() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White, modifier = Modifier.size(18.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = event.title,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        RowInfoBox(
                            title = "DATE",
                            value = "${event.dateMonth} ${event.dateDay}, 2026",
                            icon = "📅",
                            modifier = Modifier.weight(1f)
                        )
                        RowInfoBox(
                            title = "TIME",
                            value = event.time,
                            icon = "🕒",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    RowInfoBox(
                        title = "LOCATION",
                        value = event.location,
                        icon = "📍",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFF003D4C), RoundedCornerShape(12.dp))
                            .background(Color(0xFF061825))
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("👥", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Available Spots", color = Color.White, fontSize = 14.sp)
                            }
                            Text(
                                "8 / 50",
                                color = Color(0xFF00E5FF),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Text("Description", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(
                        text = "Learn the fundamentals of user interface and experience design with hands-on projects.",
                        color = Color.Gray,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onJoinClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isJoined) Color(0xFF00E676) else Color.White
                        ),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text(
                            text = if (isJoined) "Joined / Registered ✓" else "Join Activity",
                            color = if (isJoined) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RowInfoBox(title: String, value: String, icon: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFF091522), RoundedCornerShape(12.dp))
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(icon, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(title, color = Color.Gray, fontSize = 10.sp)
                Text(value, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}