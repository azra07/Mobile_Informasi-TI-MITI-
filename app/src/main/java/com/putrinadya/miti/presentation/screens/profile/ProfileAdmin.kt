package com.putrinadya.miti.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.putrinadya.miti.presentation.MainViewModel
import com.putrinadya.miti.presentation.navigation.Screen
import com.putrinadya.miti.presentation.screens.admin.dashboard.AdminDashboardViewModel
import com.putrinadya.miti.ui.theme.*

// IMPORT SELEURUH KOMPONEN BERSAMA DAN DIALOG EDIT ADMIN BARU DARI COMPONENTPROFIL
import com.putrinadya.miti.presentation.screens.profile.ComponentProfilStudent.ProfileIdentityCard
import com.putrinadya.miti.presentation.screens.profile.ComponentProfilStudent.DarkModeRow
import com.putrinadya.miti.presentation.screens.profile.ComponentProfilStudent.LogoutButton
import com.putrinadya.miti.presentation.screens.profile.ComponentProfilAdmin.EditAdminProfileDialog

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

    // Mencegah dialog menutup sendiri saat layar dirotasi
    var showEditDialog by rememberSaveable { mutableStateOf(false) }
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
                Icon(Icons.Default.ArrowBack, contentDescription = "Kembali", tint = MaterialTheme.colorScheme.onBackground)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Profil Admin",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // 1. KARTU IDENTITAS UTAMA (MENGGUNAKAN KOMPONEN BERSAMA)
            item {
                val initials = if (adminName.length >= 2) adminName.take(2).uppercase() else "AD"
                ProfileIdentityCard(
                    name = adminName,
                    initials = initials,
                    onEditClick = { showEditDialog = true },
                    details = listOf(
                        "👤" to "NIP: $adminNip",
                        "💼" to uiState.adminTitle.ifEmpty { "Admin Utama" },
                        "✉️" to adminEmail
                    )
                )
            }

            // 2. KARTU PENGATURAN TEMA (MENGGUNAKAN KOMPONEN BERSAMA)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        DarkModeRow(
                            isDarkMode = isDarkMode,
                            onCheckedChange = { mainViewModel.toggleTheme(it) }
                        )
                    }
                }
            }

            // 3. TOMBOL LOGOUT (MENGGUNAKAN KOMPONEN BERSAMA YANG AMAN)
            item {
                LogoutButton(
                    onClick = {
                        try {
                            viewModel.logout() // 1. Keluar dari Firebase Auth
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.StudentDashboard.route) { inclusive = true }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            try {
                                navController.navigate(Screen.Login.route)
                            } catch (navEx: Exception) {
                                navEx.printStackTrace()
                            }
                        }
                    }
                )
            }
        }
    }

    // 4. DIALOG EDIT ADMIN (BEBAS FOTO & AMAN DARI ROTASI)
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

                // Simpan perubahan secara permanen ke database Firestore
                viewModel.updateAdminProfile(name, nip, email, uiState.adminPhotoUrl)
                showEditDialog = false
            }
        )
    }
}