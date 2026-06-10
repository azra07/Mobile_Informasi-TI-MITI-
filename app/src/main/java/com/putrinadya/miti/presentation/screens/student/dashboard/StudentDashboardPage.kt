package com.putrinadya.miti.presentation.screens.student.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.putrinadya.miti.R
import com.putrinadya.miti.presentation.components.FeaturedEventCard
import com.putrinadya.miti.presentation.components.UpcomingActivityCard
import com.putrinadya.miti.presentation.components.RegistrationPopUp
import com.putrinadya.miti.presentation.screens.student.calendar.CalendarPage
import com.putrinadya.miti.presentation.screens.profile.ProfileStudent
import com.putrinadya.miti.ui.theme.* // Mengimpor tema warna Miti baru Anda

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboardPage(viewModel: StudentDashboardViewModel) {
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
                        selectedIconColor = MitiNavy,
                        selectedTextColor = MitiCyan,
                        indicatorColor = MitiCyan,
                        unselectedIconColor = MitiGray,
                        unselectedTextColor = MitiGray
                    )
                )
                NavigationBarItem(
                    selected = viewModel.selectedTab == 1,
                    onClick = { viewModel.onTabSelected(1) },
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Calendar") },
                    label = { Text("Calendar") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MitiNavy,
                        selectedTextColor = MitiCyan,
                        indicatorColor = MitiCyan,
                        unselectedIconColor = MitiGray,
                        unselectedTextColor = MitiGray
                    )
                )
                NavigationBarItem(
                    selected = viewModel.selectedTab == 2,
                    onClick = { viewModel.onTabSelected(2) },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MitiNavy,
                        selectedTextColor = MitiCyan,
                        indicatorColor = MitiCyan,
                        unselectedIconColor = MitiGray,
                        unselectedTextColor = MitiGray
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MitiNavy) // Menggunakan warna MitiNavy kustom dari Color.kt
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
                                    Text(
                                        text = stringResource(R.string.app_name), // Menggunakan strings.xml
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MitiWhite
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        "Good Evening,",
                                        fontSize = 14.sp,
                                        color = MitiGray
                                    )
                                    Text(
                                        "Alex!",
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MitiWhite
                                    )
                                }
                                Box(
                                    modifier = Modifier.size(45.dp)
                                        .background(MitiCyan, shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "AJ",
                                        fontWeight = FontWeight.Bold,
                                        color = MitiNavy,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }

                        item {
                            OutlinedTextField(
                                value = viewModel.searchQuery,
                                onValueChange = { viewModel.onSearchQueryChanged(it) },
                                placeholder = {
                                    Text(
                                        "Search events, workshops...",
                                        color = MitiGray
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = "Search",
                                        tint = MitiGray
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = MitiWhite,
                                    unfocusedTextColor = MitiWhite,
                                    focusedContainerColor = MitiCard, // Menggunakan MitiCard kustom dari Color.kt
                                    unfocusedContainerColor = MitiCard,
                                    focusedBorderColor = MitiCyan,
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
                                    Text(
                                        "Featured Tech Events",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MitiWhite
                                    )
                                    Text("See all", fontSize = 14.sp, color = MitiCyan)
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
                                Text(
                                    "Upcoming Activities",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MitiWhite
                                )
                                Text("View all", fontSize = 14.sp, color = MitiCyan)
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
                    CalendarPage(viewModel = viewModel)
                }

                2 -> {
                    ProfileStudent(viewModel = viewModel)
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