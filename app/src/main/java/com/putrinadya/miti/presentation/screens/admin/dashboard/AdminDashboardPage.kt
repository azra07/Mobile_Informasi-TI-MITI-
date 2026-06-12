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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.putrinadya.miti.R
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.presentation.screens.profile.ProfileAdmin
import com.putrinadya.miti.ui.theme.*
import androidx.navigation.NavController

@Composable
fun AdminDashboardPage(
    viewModel: AdminDashboardViewModel,
    navController: NavController // <--- 2. Terima parameter ini di sini
) {
    val uiState = viewModel.uiState

    if (uiState.currentSubScreen == "profile") {
        // Mengirimkan instance viewModel agar halaman ProfileAdmin memuat data NIP, Nama, dll secara dinamis
        ProfileAdmin(
            viewModel = viewModel,
            navController = navController,
            onBackClick = { viewModel.onNavigateToSubScreen("dashboard") }
        )
    } else {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { viewModel.onShowCreateEventDialog(true) },
                    containerColor = MitiCyan,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Event", tint = MitiNavy)
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MitiNavy)
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
                            Text(stringResource(id = R.string.app_name), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MitiWhite)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(8.dp).background(Color.Green, CircleShape))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Admin Mode", fontSize = 12.sp, color = MitiGray)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "Selamat datang!", fontSize = 14.sp, color = MitiGray)

                            // MEMBACA NAMA ADMIN SECARA DINAMIS DARI FIRESTORE DATABASE
                            Text("${uiState.adminName}!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MitiWhite)
                        }

                        // Avatar DSC (Menampilkan Inisial Nama Admin Secara Dinamis)
                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .background(MitiCyan, shape = CircleShape)
                                .clickable { viewModel.onNavigateToSubScreen("profile") },
                            contentAlignment = Alignment.Center
                        ) {
                            val initials = if (uiState.adminName.length >= 2) uiState.adminName.take(2).uppercase() else "AD"
                            Text(initials, fontWeight = FontWeight.Bold, color = MitiNavy, fontSize = 15.sp)
                        }
                    }
                }

                // Statistik Row
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
                        Text("Manage Events", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MitiWhite)
                        Box(
                            modifier = Modifier
                                .background(MitiCard, RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("${viewModel.eventsList.size} events", color = MitiGray, fontSize = 12.sp)
                        }
                    }
                }

                // Daftar Event
                items(viewModel.eventsList) { event ->
                    AdminEventCard(
                        event = event,
                        onEdit = { viewModel.onEditEventClick(event) },
                        onDelete = { viewModel.removeEvent(event) }
                    )
                }
            }

            // POP-UP FORM DIALOG: CREATE NEW EVENT
            if (uiState.showCreateEventDialog) {
                CreateNewEventDialog(
                    onClose = { viewModel.onShowCreateEventDialog(false) },
                    onSave = { newEvent ->
                        viewModel.createNewEvent(newEvent)
                    }
                )
            }

            // POP-UP FORM DIALOG: EDIT EXISTING EVENT (Update)
            if (uiState.showEditEventDialog && uiState.selectedEventForEdit != null) {
                EditEventDialog(
                    event = uiState.selectedEventForEdit,
                    onClose = { viewModel.onCancelEdit() },
                    onSave = { updatedEvent ->
                        viewModel.updateEvent(updatedEvent)
                    }
                )
            }
        }
    }
}