package com.example.miti.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Composable
fun AdminDashboardScreen() {
    val backgroundColor = Color(0xFF030A16)
    val cardColor = Color(0xFF091522)
    val primaryCyan = Color(0xFF00E5FF)
    val textWhite = Color(0xFFFFFFFF)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Navigasi ke Form Tambah Event */ },
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
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

                    // Avatar DSC
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .background(primaryCyan, shape = CircleShape),
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
                    StatCard(title = "Total Events", value = "8", iconText = "📅", modifier = Modifier.weight(1f))
                    StatCard(title = "Active Participants", value = "643", iconText = "👥", modifier = Modifier.weight(1.1f))
                    StatCard(title = "New Aspirations", value = "12", iconText = "💡", modifier = Modifier.weight(1f))
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
                        Text("8 events", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }

            // Daftar Event
            items(dummyEvents) { event ->
                AdminEventCard(event = event)
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
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(Color(0xFF142233), CircleShape),
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF091522)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Garis samping berwarna kategori di paling kiri
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .height(95.dp)
                    .background(event.categoryColor)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
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

                IconButton(onClick = { /* Aksi edit/delete event */ }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = Color.Gray)
                }
            }
        }
    }
}