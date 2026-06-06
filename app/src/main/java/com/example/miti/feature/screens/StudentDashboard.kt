package com.example.miti.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.miti.data.Event
import com.example.miti.data.dummyEvents
import androidx.compose.material3.OutlinedTextFieldDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboardScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }

    val backgroundColor = Color(0xFF030A16)
    val cardColor = Color(0xFF091522)
    val primaryCyan = Color(0xFF00E5FF)
    val textWhite = Color(0xFFFFFFFF)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF040F1F),
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
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
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
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
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("MITI", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = textWhite)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Good Evening,", fontSize = 14.sp, color = Color.Gray)
                        Text("Alex!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = textWhite)
                    }
                    // Avatar Lingkaran AJ
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .background(primaryCyan, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("AJ", fontWeight = FontWeight.Bold, color = backgroundColor, fontSize = 16.sp)
                    }
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search events, workshops...", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
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

            // Section 1: Featured Tech Events
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Featured Tech Events", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textWhite)
                        Text("See all", fontSize = 14.sp, color = primaryCyan)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(dummyEvents.take(3)) { event ->
                            FeaturedEventCard(event = event)
                        }
                    }
                }
            }

            // Section 2: Upcoming Activities
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Upcoming Activities", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textWhite)
                    Text("View all", fontSize = 14.sp, color = primaryCyan)
                }
            }

            items(dummyEvents) { event ->
                UpcomingActivityCard(event = event)
            }
        }
    }
}

@Composable
fun FeaturedEventCard(event: Event) {
    Box(
        modifier = Modifier
            .width(260.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF00E5FF))
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

@Composable
fun UpcomingActivityCard(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
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
            // Box Tanggal di Kiri
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

            // Info Event di Tengah
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(event.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(event.time, fontSize = 11.sp, color = Color.Gray)
                    Text(" • ", fontSize = 11.sp, color = Color.Gray)
                    Text(event.location, fontSize = 11.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

            // Kategori Badge di Kanan
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