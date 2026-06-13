package com.putrinadya.miti.presentation.screens.admin.dashboard

import androidx.compose.foundation.Image // Import untuk memuat gambar
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
import androidx.compose.ui.res.painterResource // Import untuk memanggil drawable resource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.putrinadya.miti.R
import com.putrinadya.miti.presentation.screens.profile.ProfileAdmin
import androidx.navigation.NavController

@Composable
fun AdminDashboardPage(
    viewModel: AdminDashboardViewModel,
    navController: NavController
) {
    val uiState = viewModel.uiState
    var showDeleteAllConfirm by remember { mutableStateOf(false) }

    if (uiState.currentSubScreen == "profile") {
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
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah Kegiatan", tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background) // MitiNavy
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Admin (GANTI TULISAN DENGAN GAMBAR LOGO & ADJUST SAPAAN)
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            // 1. Memanggil Gambar logo.jpeg dari drawable pengganti teks MITI
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Logo MITI",
                                modifier = Modifier
                                    .height(35.dp)
                                    .padding(bottom = 4.dp)
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(8.dp).background(Color.Green, CircleShape))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Mode Admin", // Bahasa Indonesia
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // 2. Sapaan Selamat datang dan Nama Admin diletakkan sejajar sebaris
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Selamat datang, ",
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                )
                                Text(
                                    text = "${uiState.adminName}!",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                        // Avatar DSC (Inisial Nama Admin)
                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                                .clickable { viewModel.onNavigateToSubScreen("profile") },
                            contentAlignment = Alignment.Center
                        ) {
                            val initials = if (uiState.adminName.length >= 2) uiState.adminName.take(2).uppercase() else "AD"
                            Text(
                                text = initials,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 15.sp
                            )
                        }
                    }
                }

                // Statistik Row (MENYISAKAN 3 KARTU STATISTIK SEIMBANG)
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        StatCard(title = "Total Kegiatan", value = uiState.totalEvents.toString(), iconText = "📅", modifier = Modifier.weight(1f))
                        StatCard(title = "Aspirasi", value = uiState.newAspirations.toString(), iconText = "💡", modifier = Modifier.weight(1f))
                        StatCard(
                            title = "Total Mahasiswa",
                            value = uiState.totalStudents.toString(),
                            iconText = "🎓",
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    viewModel.onShowRegisterDialog(true)
                                }
                        )
                    }
                }

                // Header Daftar Aspirasi
                item {
                    Text(
                        text = "Daftar Aspirasi Mahasiswa",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Daftar Aspirasi
                items(uiState.aspirationsList) { aspirasi ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = aspirasi.category,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = aspirasi.content,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (aspirasi.anonymous) "Dari: Anonim" else "Dari: ${aspirasi.userName}",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                // Manage Events Header (KINI "Kelola Kegiatan" BAHASA INDONESIA)
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Kelola Kegiatan",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "${viewModel.eventsList.size} kegiatan", // Bahasa Indonesia
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            if (viewModel.eventsList.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Hapus Semua",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .clickable { showDeleteAllConfirm = true }
                                        .padding(8.dp)
                                )
                            }
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

            if (uiState.showRegisterUserDialog) {
                RegisterStudentDialog(
                    onClose = { viewModel.onShowRegisterDialog(false) },
                    onRegister = { name, email, pass, nim ->
                        viewModel.registerNewStudent(name, email, pass, nim)
                    },
                    isLoading = uiState.isRegistering
                )
            }
        }

        if (showDeleteAllConfirm) {
            AlertDialog(
                onDismissRequest = { showDeleteAllConfirm = false },
                containerColor = MaterialTheme.colorScheme.surface,
                title = { Text("Hapus Semua Kegiatan?", color = MaterialTheme.colorScheme.onBackground) },
                text = { Text("Tindakan ini tidak dapat dibatalkan. Semua data kegiatan akan hilang permanen.", color = MaterialTheme.colorScheme.onBackground) },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.clearAllEvents()
                        showDeleteAllConfirm = false
                    }) {
                        Text("Hapus", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteAllConfirm = false }) {
                        Text("Batal", color = MaterialTheme.colorScheme.onBackground)
                    }
                }
            )
        }
    }
}