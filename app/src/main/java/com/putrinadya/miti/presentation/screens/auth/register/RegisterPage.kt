package com.putrinadya.miti.presentation.screens.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.putrinadya.miti.presentation.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(
    viewModel: RegisterViewModel = viewModel(),
    onBackClick: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val uiState = viewModel.uiState

    val backgroundColor = Color(0xFF030A16)
    val cardColor = Color(0xFF091522)
    val primaryCyan = Color(0xFF00E5FF)
    val textWhite = Color(0xFFFFFFFF)

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onRegisterSuccess()
            viewModel.resetSuccessState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // TopBar global dengan tombol kembali
        TopBar(title = "Create Account", onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Judul
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "Register Student",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = textWhite
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Join the IT Activity Hub community",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Card Form Input Registrasi
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f, fill = false).padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Error Message
                    uiState.error?.let {
                        Text(text = it, color = Color.Red, fontSize = 12.sp)
                    }

                    Column {
                        Text(text = "Full Name", color = textWhite, fontSize = 12.sp)
                        OutlinedTextField(
                            value = uiState.name,
                            onValueChange = { viewModel.onNameChanged(it) },
                            placeholder = { Text("e.g., Alex Johnson", color = Color.Gray) },
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = textWhite,
                                unfocusedTextColor = textWhite,
                                focusedContainerColor = backgroundColor,
                                unfocusedContainerColor = backgroundColor,
                                focusedBorderColor = primaryCyan,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )
                    }

                    Column {
                        Text(text = "Email Address", color = textWhite, fontSize = 12.sp)
                        OutlinedTextField(
                            value = uiState.email,
                            onValueChange = { viewModel.onEmailChanged(it) },
                            placeholder = { Text("alex.johnson@university.edu", color = Color.Gray) },
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = textWhite,
                                unfocusedTextColor = textWhite,
                                focusedContainerColor = backgroundColor,
                                unfocusedContainerColor = backgroundColor,
                                focusedBorderColor = primaryCyan,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )
                    }

                    Column {
                        Text(text = "NIM (Student ID)", color = textWhite, fontSize = 12.sp)
                        OutlinedTextField(
                            value = uiState.nim,
                            onValueChange = { viewModel.onNimChanged(it) },
                            placeholder = { Text("2024123456", color = Color.Gray) },
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = textWhite,
                                unfocusedTextColor = textWhite,
                                focusedContainerColor = backgroundColor,
                                unfocusedContainerColor = backgroundColor,
                                focusedBorderColor = primaryCyan,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )
                    }

                    Column {
                        Text(text = "Password", color = textWhite, fontSize = 12.sp)
                        OutlinedTextField(
                            value = uiState.password,
                            onValueChange = { viewModel.onPasswordChanged(it) },
                            placeholder = { Text("Create secure password", color = Color.Gray) },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = textWhite,
                                unfocusedTextColor = textWhite,
                                focusedContainerColor = backgroundColor,
                                unfocusedContainerColor = backgroundColor,
                                focusedBorderColor = primaryCyan,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )
                    }
                }
            }

            // Button Buat Akun
            Button(
                onClick = { viewModel.register() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryCyan),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(text = "Create Account", color = backgroundColor, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}