package com.putrinadya.miti.presentation.screens.auth.login

import androidx.compose.foundation.Image // Import untuk memuat gambar logo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource // Import untuk memanggil logo drawable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.putrinadya.miti.R
import com.putrinadya.miti.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToStudentDashboard: () -> Unit,
    onNavigateToAdminDashboard: () -> Unit
) {
    val uiState = viewModel.uiState
    val scrollState = rememberScrollState() // State scroll untuk posisi landscape

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            if (uiState.userRole == "admin") {
                onNavigateToAdminDashboard()
            } else {
                onNavigateToStudentDashboard()
            }
            viewModel.resetSuccessState()
        }
    }

    // Penerjemah Peringatan Firebase Inggris ke Bahasa Indonesia secara Akurat
    val translatedError = remember(uiState.error) {
        when {
            uiState.error == null -> null
            uiState.error!!.contains("badly formatted", ignoreCase = true) ->
                "Format alamat email salah atau tidak valid."
            uiState.error!!.contains("user-not-found", ignoreCase = true) || uiState.error!!.contains("no user record", ignoreCase = true) ->
                "Akun tidak ditemukan. Silakan hubungi admin kampus Anda."
            uiState.error!!.contains("wrong-password", ignoreCase = true) ||
                    uiState.error!!.contains("invalid-credential", ignoreCase = true) ||
                    uiState.error!!.contains("incorrect", ignoreCase = true) ||
                    uiState.error!!.contains("credential", ignoreCase = true) ||
                    uiState.error!!.contains("expired", ignoreCase = true) ->
                "Alamat email atau kata sandi salah. Silakan periksa kembali."
            uiState.error!!.contains("network", ignoreCase = true) ->
                "Koneksi internet bermasalah. Periksa koneksi Anda."
            else -> uiState.error
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState) // Mengaktifkan fungsi scroll saat posisi HP landscape
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header (MEMPERBESAR LOGO ASLI ANDA & MENGHAPUS BOX BINTANG)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            // 1. Memanggil Gambar logo.jpeg asli Anda dengan ukuran lebih besar (90.dp)
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo MITI",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 12.dp)
            )

            Text(
                text = "Masuk untuk melanjutkan", // Bahasa Indonesia murni
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Card Login Form
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Selamat Datang", // Bahasa Indonesia murni
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Error Message Terjemahan Indonesia
                translatedError?.let {
                    Text(text = it, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(bottom = 8.dp))
                }

                Text(text = "Alamat Email", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp) // Bahasa Indonesia
                OutlinedTextField(
                    value = uiState.emailOrNim,
                    onValueChange = { viewModel.onEmailOrNimChanged(it) },
                    placeholder = { Text("Masukkan alamat email Anda", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
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

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Kata Sandi", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp) // Bahasa Indonesia
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    placeholder = { Text("Masukkan kata sandi Anda", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
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

                Spacer(modifier = Modifier.height(8.dp))

                // Lupa Kata Sandi (Bahasa Indonesia murni)
                Text(
                    text = "Lupa Kata Sandi?",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { viewModel.sendForgotPassword(uiState.emailOrNim) }
                        .padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons (PERMANEN BAHASA INDONESIA TANPA PENGARUH BAHASA EMULATOR)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { viewModel.login("mahasiswa") },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Masuk sebagai Mahasiswa", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold) // Bahasa Indonesia Permanen
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { viewModel.login("admin") },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary)
                )
            ) {
                Text(text = "Masuk sebagai Admin", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold) // Bahasa Indonesia Permanen
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Teknologi Informasi | Universitas Lambung Mangkurat", // Bahasa Indonesia murni
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}