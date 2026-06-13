package com.putrinadya.miti.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.putrinadya.miti.presentation.screens.admin.dashboard.AdminDashboardViewModel
import com.putrinadya.miti.ui.theme.*
import androidx.navigation.NavController
import com.putrinadya.miti.presentation.MainViewModel
import com.putrinadya.miti.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileAdmin(
    viewModel: AdminDashboardViewModel,
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState = viewModel.uiState

    var adminName by remember(uiState.adminName) { mutableStateOf(uiState.adminName) }
    var adminNip by remember(uiState.adminNip) { mutableStateOf(uiState.adminNip) }
    var adminEmail by remember(uiState.adminEmail) { mutableStateOf(uiState.adminEmail) }
    var showEditDialog by remember { mutableStateOf(false) }

    val isDarkMode by mainViewModel.isDarkMode.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Kembali", tint = MitiWhite)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Profil Admin",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MitiWhite
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Card Utama
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(MaterialTheme.colorScheme.primary)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .offset(y = (-40).dp)
                                    .size(80.dp)
                                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                                    .border(4.dp, MaterialTheme.colorScheme.surface, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                val initials = if (adminName.length >= 2) adminName.take(2).uppercase() else "AD"
                                Text(initials, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.background, fontSize = 22.sp)
                            }

                            OutlinedButton(
                                onClick = { showEditDialog = true },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 8.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = SolidColor(MaterialTheme.colorScheme.primary)
                                )
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Edit Profil", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 12.dp)
                        ) {
                            Text(adminName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MitiWhite)
                            Spacer(modifier = Modifier.height(12.dp))

                            // Seluruh data diambil secara dinamis dari database Firestore
                            ProfileDetailRow(icon = "👤", label = "NIP / Staff ID: $adminNip")
                            ProfileDetailRow(icon = "💼", label = uiState.adminTitle.ifEmpty { "Dosen" })
                            ProfileDetailRow(icon = "✉️", label = adminEmail)
                            ProfileDetailRow(icon = "🏛️", label = uiState.adminDepartment.ifEmpty { "Teknologi Informasi" })
                        }
                    }
                }
            }

            // Pengaturan Dark Mode & Bahasa
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🌙", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Mode Gelap", fontSize = 14.sp, color = MitiWhite, fontWeight = FontWeight.Bold)
                                    Text(if (isDarkMode) "Aktif" else "Nonaktif", fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground)
                                }
                            }
                            Switch(
                                checked = isDarkMode,
                                onCheckedChange = { mainViewModel.toggleTheme(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.background,
                                    checkedTrackColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }

                        Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🌐", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Bahasa", fontSize = 14.sp, color = MitiWhite, fontWeight = FontWeight.Bold)
                                    Text("Bahasa Indonesia", fontSize = 11.sp, color = MitiGray)
                                }
                            }
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Pergi", tint = MitiGray)
                        }
                    }
                }
            }

            // Tombol Logout
            item {
                OutlinedButton(
                    onClick = {
                        viewModel.logout() // 1. Keluar dari Firebase Auth

                        // 2. Navigasi Aman: Arahkan ke Login dan hapus rute Dashboard Admin dari riwayat
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.AdminDashboard.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF3B30)),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = SolidColor(Color(0xFF2E0C0C))
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Keluar Akun", modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Keluar Akun", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
            }
        }
    }

    if (showEditDialog) {
        EditAdminProfileDialog(
            currentName = adminName,
            currentNip = adminNip,
            currentEmail = adminEmail,
            onClose = { showEditDialog = false },
            onSave = { name, nip, email ->
                adminName = name
                adminNip = nip
                adminEmail = email
                showEditDialog = false
            }
        )
    }
}

// ================= COMPONENT PEMBANTU: DETAIL ROW (DIBUAT PRIVATE) =================
@Composable
private fun ProfileDetailRow(icon: String, label: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, fontSize = 13.sp, color = MitiGray)
    }
}

// ================= DIALOG: POP-UP EDIT PROFILE ADMIN =================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAdminProfileDialog(
    currentName: String,
    currentNip: String,
    currentEmail: String,
    onClose: () -> Unit,
    onSave: (name: String, nip: String, email: String) -> Unit
) {
    var nameInput by remember { mutableStateOf(currentName) }
    var nipInput by remember { mutableStateOf(currentNip) }
    var emailInput by remember { mutableStateOf(currentEmail) }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Edit Profil", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MitiWhite)
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(MitiWhite.copy(alpha = 0.1f), CircleShape)
                            .clickable { onClose() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup", tint = MitiWhite, modifier = Modifier.size(16.dp))
                    }
                }

                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        // Menggunakan inisial nama edit dinamis (contoh: "amang" -> "AM")
                        val initials = if (nameInput.length >= 2) nameInput.take(2).uppercase() else "AD"
                        Text(initials, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.background, fontSize = 26.sp)
                    }
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Ubah Foto", tint = MaterialTheme.colorScheme.background, modifier = Modifier.size(16.dp))
                    }
                }
                Text("Ketuk untuk mengubah foto", fontSize = 12.sp, color = MitiGray)

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Nama Lengkap", color = MitiWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MitiWhite,
                            unfocusedTextColor = MitiWhite,
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("NIP / Staff ID", color = MitiWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = nipInput,
                        onValueChange = { nipInput = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MitiWhite,
                            unfocusedTextColor = MitiWhite,
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Alamat Email", color = MitiWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MitiWhite,
                            unfocusedTextColor = MitiWhite,
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onClose,
                        modifier = Modifier.weight(1f).height(45.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF142233)),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Text("Batal", color = MitiWhite, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { onSave(nameInput, nipInput, emailInput) },
                        modifier = Modifier.weight(1.3f).height(45.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Check, contentDescription = "Simpan", tint = MaterialTheme.colorScheme.background, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Simpan Perubahan", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}