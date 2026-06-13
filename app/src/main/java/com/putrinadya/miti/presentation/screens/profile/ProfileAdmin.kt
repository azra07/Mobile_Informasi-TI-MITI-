package com.putrinadya.miti.presentation.screens.profile

import android.net.Uri // MENAMBAHKAN IMPORT URI
import androidx.activity.compose.rememberLauncherForActivityResult // IMPORT GALLERY LAUNCHER
import androidx.activity.result.contract.ActivityResultContracts // IMPORT LAUNCHER CONTRACT
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable // IMPORT REMEMBER SAVEABLE AGAR ANTI-ROTASI
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
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
    var adminPhotoUrl by remember(uiState.adminPhotoUrl) { mutableStateOf(uiState.adminPhotoUrl) }

    // GANTI REMEMBER MENJADI REMEMBER SAVEABLE: Mencegah dialog menutup sendiri saat layar dirotasi
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
                                // Menampilkan foto profil dinamis baik dari galeri lokal maupun internet
                                if (adminPhotoUrl.isNotBlank()) {
                                    AsyncImage(
                                        model = adminPhotoUrl,
                                        contentDescription = "Foto Profil",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize().clip(CircleShape)
                                    )
                                } else {
                                    val initials = if (adminName.length >= 2) adminName.take(2).uppercase() else "AD"
                                    Text(initials, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary, fontSize = 22.sp)
                                }
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
                            Text(adminName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            Spacer(modifier = Modifier.height(12.dp))

                            ProfileDetailRow(icon = "👤", label = adminNip)
                            ProfileDetailRow(icon = "💼", label = uiState.adminTitle.ifEmpty { "Admin Utama" })
                            ProfileDetailRow(icon = "✉️", label = adminEmail)
                        }
                    }
                }
            }

            // Pengaturan Dark Mode
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
                                    Text("Mode Gelap", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                                    Text(if (isDarkMode) "Aktif" else "Nonaktif", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
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
                    }
                }
            }

            // Tombol Logout
            item {
                OutlinedButton(
                    onClick = {
                        try {
                            viewModel.logout() // 1. Keluar dari Firebase Auth

                            // 2. Navigasi Aman: Arahkan ke Login dan hapus rute Dashboard Admin dari riwayat
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.AdminDashboard.route) { inclusive = true }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            try {
                                navController.navigate(Screen.Login.route)
                            } catch (navEx: Exception) {
                                navEx.printStackTrace()
                            }
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
            currentPhotoUrl = adminPhotoUrl,
            onClose = { showEditDialog = false },
            onSave = { name, nip, email, photoUrl ->
                adminName = name
                adminNip = nip
                adminEmail = email
                adminPhotoUrl = photoUrl

                // Simpan perubahan secara permanen ke database Firestore
                viewModel.updateAdminProfile(name, nip, email, photoUrl)
                showEditDialog = false
            }
        )
    }
}

// ================= COMPONENT PEMBANTU: DETAIL ROW =================
@Composable
private fun ProfileDetailRow(icon: String, label: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

// ================= DIALOG: POP-UP EDIT PROFILE ADMIN (ANTI-ROTASI & GALLERY PICKER) =================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAdminProfileDialog(
    currentName: String,
    currentNip: String,
    currentEmail: String,
    currentPhotoUrl: String,
    onClose: () -> Unit,
    onSave: (name: String, nip: String, email: String, photoUrl: String) -> Unit
) {
    // MENGGUNAKAN REMEMBER SAVEABLE: Menjamin data input tidak hilang saat layar diputar/dirotasi
    var nameInput by rememberSaveable { mutableStateOf(currentName) }
    var nipInput by rememberSaveable { mutableStateOf(currentNip) }
    var emailInput by rememberSaveable { mutableStateOf(currentEmail) }
    var photoUrlInput by rememberSaveable { mutableStateOf(currentPhotoUrl) }

    val scrollState = rememberScrollState() // State scroll internal form dialog

    // launcher untuk mengakses Galeri Foto HP asli
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            photoUrlInput = uri.toString() // Menyimpan alamat URI foto lokal dari galeri HP
        }
    }

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
                    .padding(20.dp)
                    .verticalScroll(scrollState), // Mengaktifkan scroll agar form muat di posisi layar landscape
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Edit Profil",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), CircleShape)
                            .clickable { onClose() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Close,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Tutup",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // AVATAR DENGAN TOMBOL GALLERY PICKER (+)
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.clickable { galleryLauncher.launch("image/*") } // Klik lingkaran untuk buka galeri HP
                ) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        if (photoUrlInput.isNotBlank()) {
                            AsyncImage(
                                model = photoUrlInput,
                                contentDescription = "Foto Profil",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize().clip(CircleShape)
                            )
                        } else {
                            val initials = if (nameInput.length >= 2) nameInput.take(2).uppercase() else "AD"
                            Text(initials, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary, fontSize = 26.sp)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
                            .clickable { galleryLauncher.launch("image/*") }, // Klik tombol + untuk buka galeri HP
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Ubah Foto", tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(16.dp))
                    }
                }
                Text(
                    text = "Ketuk untuk mengubah foto",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Nama Lengkap", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("NIP / Staff ID", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
                    OutlinedTextField(
                        value = nipInput,
                        onValueChange = { nipInput = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Alamat Email", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
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
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Text("Batal", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { onSave(nameInput, nipInput, emailInput, photoUrlInput) },
                        modifier = Modifier.weight(1.3f).height(45.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Check, contentDescription = "Simpan", tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Simpan", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}