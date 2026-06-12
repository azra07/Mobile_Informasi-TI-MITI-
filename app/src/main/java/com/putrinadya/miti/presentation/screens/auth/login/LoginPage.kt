package com.putrinadya.miti.presentation.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
            .background(MitiNavy)
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
                    .background(MitiCyan, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "✦", fontSize = 36.sp, color = MitiNavy)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MitiWhite
            )
            Text(
                text = stringResource(id = R.string.login_subtitle),
                fontSize = 14.sp,
                color = MitiGray
            )
        }

        // Card Login Form
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MitiCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(id = R.string.login_welcome),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MitiWhite,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Error Message
                uiState.error?.let {
                    Text(text = it, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(bottom = 8.dp))
                }

                Text(text = stringResource(id = R.string.email_label), color = MitiWhite, fontSize = 12.sp)
                OutlinedTextField(
                    value = uiState.emailOrNim,
                    onValueChange = { viewModel.onEmailOrNimChanged(it) },
                    placeholder = { Text("Enter your email", color = MitiGray) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MitiWhite,
                        unfocusedTextColor = MitiWhite,
                        focusedContainerColor = MitiNavy,
                        unfocusedContainerColor = MitiNavy,
                        focusedBorderColor = MitiCyan,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = stringResource(id = R.string.password_label), color = MitiWhite, fontSize = 12.sp)
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    placeholder = { Text("Enter your password", color = MitiGray) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MitiWhite,
                        unfocusedTextColor = MitiWhite,
                        focusedContainerColor = MitiNavy,
                        unfocusedContainerColor = MitiNavy,
                        focusedBorderColor = MitiCyan,
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
                onClick = { viewModel.login("mahasiswa") },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MitiCyan),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = stringResource(id = R.string.login_as_student), color = MitiNavy, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { viewModel.login("admin") },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MitiCyan),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(MitiCyan)
                )
            ) {
                Text(text = stringResource(id = R.string.login_as_admin), color = MitiCyan, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Teknologi Informasi | University Lambung Mangkurat",
                fontSize = 11.sp,
                color = MitiGray
            )
        }
    }
}