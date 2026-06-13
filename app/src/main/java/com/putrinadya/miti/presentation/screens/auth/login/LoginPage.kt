package com.putrinadya.miti.presentation.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // IMPORT UNTUK SCROLLING
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll // IMPORT UNTUK SCROLLING
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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

    // Penerjemah Peringatan Firebase Inggris ke Bahasa Indonesia
    val translatedError = remember(uiState.error) {
        when {
            uiState.error == null -> null
            uiState.error!!.contains("badly formatted", ignoreCase = true) ->
                "Format alamat email salah atau tidak valid."
            uiState.error!!.contains("user-not-found", ignoreCase = true) || uiState.error!!.contains("no user record", ignoreCase = true) ->
                "Akun tidak ditemukan. Silakan hubungi admin kampus Anda."
            uiState.error!!.contains("wrong-password", ignoreCase = true) || uiState.error!!.contains("invalid-credential", ignoreCase = true) ->
                "Kata sandi salah. Silakan coba lagi."
            uiState.error!!.contains("network", ignoreCase = true) ->
                "Koneksi internet bermasalah. Periksa koneksi Anda."
            else -> uiState.error
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // MitiNavy di Dark Mode, Putih di Light Mode
            .verticalScroll(scrollState) // 1. Tambahkan fungsi scroll agar bisa di-scroll saat rotasi landscape
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "✦", fontSize = 36.sp, color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground // Dinamis berwarna Hitam di Light Mode
            )
            Text(
                text = stringResource(id = R.string.login_subtitle),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f) // Dinamis
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), // MitiCard di Dark, Abu Terang di Light
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(id = R.string.login_welcome),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface, // Dinamis berwarna Hitam di Light Mode
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Peringatan otomatis dalam Bahasa Indonesia
                translatedError?.let {
                    Text(text = it, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(bottom = 8.dp))
                }

                Text(text = stringResource(id = R.string.email_label), color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp) // Dinamis
                OutlinedTextField(
                    value = uiState.emailOrNim,
                    onValueChange = { viewModel.onEmailOrNimChanged(it) },
                    placeholder = { Text(stringResource(id = R.string.email_label), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface, // Dinamis
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface, // Dinamis
                        focusedContainerColor = MaterialTheme.colorScheme.background, // Putih bersih di Light Mode
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = stringResource(id = R.string.password_label), color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp) // Dinamis
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    placeholder = { Text(stringResource(id = R.string.enter_pass), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface, // Dinamis
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface, // Dinamis
                        focusedContainerColor = MaterialTheme.colorScheme.background, // Putih bersih di Light Mode
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = stringResource(id = R.string.forgot),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), // Dinamis
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable {
                                viewModel.sendForgotPassword(uiState.emailOrNim)
                            }
                            .padding(4.dp)
                    )
                }
            }
        }

        // Buttons
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
                Text(text = stringResource(id = R.string.login_as_student), color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold) // Dinamis
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
                Text(text = stringResource(id = R.string.login_as_admin), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.footer),
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f) // Dinamis
            )
        }
    }
}