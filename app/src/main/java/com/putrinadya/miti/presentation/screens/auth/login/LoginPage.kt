package com.putrinadya.miti.presentation.screens.auth.login

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToStudentDashboard: () -> Unit,
    onNavigateToAdminDashboard: () -> Unit
) {
    val uiState = viewModel.uiState

    val backgroundColor = Color(0xFF030A16)
    val cardColor = Color(0xFF091522)
    val primaryCyan = Color(0xFF00E5FF)
    val textWhite = Color(0xFFFFFFFF)

    // Efek Navigasi setelah login sukses
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header (Logo & Title)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(primaryCyan, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "✦", fontSize = 36.sp, color = backgroundColor)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "IT Activity Hub",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textWhite
            )
            Text(
                text = "Manage your university tech events",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // Card Login Form
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Welcome Back",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textWhite,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Error Message
                uiState.error?.let {
                    Text(text = it, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(bottom = 8.dp))
                }

                Text(text = "Email / NIM", color = textWhite, fontSize = 12.sp)
                OutlinedTextField(
                    value = uiState.emailOrNim,
                    onValueChange = { viewModel.onEmailOrNimChanged(it) },
                    placeholder = { Text("Enter your email or NIM", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
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

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Password", color = textWhite, fontSize = 12.sp)
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    placeholder = { Text("Enter your password", color = Color.Gray) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
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

        // Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { viewModel.login("student") },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryCyan),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Login as Student", color = backgroundColor, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { viewModel.login("admin") },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = primaryCyan),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(primaryCyan)
                )
            ) {
                Text(text = "Login as Admin / Lecturer", color = primaryCyan, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Teknologi Informasi - University Lambung Mangkurat",
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
    }
}