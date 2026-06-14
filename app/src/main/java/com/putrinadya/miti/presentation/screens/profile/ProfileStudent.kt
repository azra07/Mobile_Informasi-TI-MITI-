package com.putrinadya.miti.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.putrinadya.miti.presentation.MainViewModel
import com.putrinadya.miti.presentation.navigation.Screen
import com.putrinadya.miti.presentation.screens.profile.ComponentProfilStudent.DarkModeRow
import com.putrinadya.miti.presentation.screens.profile.ComponentProfilStudent.EditProfileDialog
import com.putrinadya.miti.presentation.screens.profile.ComponentProfilStudent.ProfileIdentityCard
import com.putrinadya.miti.presentation.screens.profile.ComponentProfilStudent.RegisteredActivityCard
import com.putrinadya.miti.presentation.screens.student.dashboard.StudentDashboardViewModel
import com.putrinadya.miti.ui.theme.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.putrinadya.miti.presentation.screens.admin.dashboard.ComponentsAdmin.AspirationDialog
import com.putrinadya.miti.presentation.screens.profile.ComponentProfilStudent.LogoutButton

@Composable
fun ProfileStudent(
    viewModel: StudentDashboardViewModel,
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val user = viewModel.studentProfile
    var showEditDialog by rememberSaveable { mutableStateOf(false) }
    val isDarkMode by mainViewModel.isDarkMode.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Profil Saya",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
        }

        // 1. KOMPONEN BERSAMA: Menggunakan ProfileIdentityCard
        item {
            if (user != null) {
                val initial = if (user.name.isNotEmpty()) user.name.take(1).uppercase() else "U"
                ProfileIdentityCard(
                    name = user.name,
                    initials = initial,
                    onEditClick = { showEditDialog = true },
                    details = listOf(
                        "👤" to user.nim,                        "✉️" to user.email
                    )
                )
            }
        }

        // Card Fitur Kirim Aspirasi
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.onShowAspirationDialog(true) },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("💡", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Kirim Aspirasi & Kritik", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Sampaikan saran atau keluhan Anda di sini", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 11.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.KeyboardArrowRight, null, tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                }
            }
        }

        // Card Total Kegiatan
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("📅", fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Total Kegiatan Diikuti:", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                    }
                    Text(viewModel.registeredEvents.size.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        item {
            Text("Daftar Kegiatan Terdaftar", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        }

        // Daftar Kegiatan Terdaftar
        if (viewModel.registeredEvents.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Belum ada kegiatan terdaftar.", color = MitiGray, fontSize = 14.sp)
                }
            }
        } else {
            items(viewModel.registeredEvents) { event ->
                RegisteredActivityCard(event = event)
            }
        }

        // Card Pengaturan Keamanan & Tema (MENGGUNAKAN KOMPONEN BERSAMA)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    // 2. KOMPONEN BERSAMA: Menggunakan DarkModeRow
                    DarkModeRow(
                        isDarkMode = isDarkMode,
                        onCheckedChange = { mainViewModel.toggleTheme(it) }
                    )

                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

                    // 3. KOMPONEN BERSAMA: Menggunakan AccountSecurityRow
                    AccountSecurityRow(
                        onClick = { viewModel.sendPasswordReset() }
                    )
                }
            }
        }

        // 4. KOMPONEN BERSAMA: Menggunakan LogoutButton
        item {
            LogoutButton(
                onClick = {
                    viewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.StudentDashboard.route) { inclusive = true }
                    }
                }
            )
        }
    }

    if (showEditDialog && user != null) {
        EditProfileDialog(
            currentName = user.name,
            currentNim = user.nim,
            currentEmail = user.email,
            onClose = { showEditDialog = false },
            onSave = { name, nim, email -> // Kembali ke 3 parameter murni sesuai strings.xml
                viewModel.updateProfile(name, nim, email) // Jalankan proses update ke database
                showEditDialog = false
            }
        )
    }

    if (viewModel.passwordResetMessage != null) {
        AlertDialog(
            onDismissRequest = { viewModel.clearPasswordMessage() },
            title = { Text("Ubah Kata Sandi", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold) },
            text = { Text(viewModel.passwordResetMessage!!, color = MaterialTheme.colorScheme.onBackground) },
            confirmButton = {
                TextButton(onClick = { viewModel.clearPasswordMessage() }) {
                    Text("Mengerti", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp)
        )
    }

    if (viewModel.showAspirationDialog) {
        AspirationDialog(
            onClose = { viewModel.onShowAspirationDialog(false) },
            onSend = { content, category, anonymous ->
                viewModel.sendAspiration(content, category, anonymous)
            }
        )
    }
}