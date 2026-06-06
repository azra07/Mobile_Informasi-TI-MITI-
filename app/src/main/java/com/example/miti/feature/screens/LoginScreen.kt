package com.example.miti.ui.screens

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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.text.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: (role: String) -> Unit
) {
    var emailOrNim by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Warna tema gelap sesuai mockup (Dark Navy & Tosca/Cyan)
    val backgroundColor = Color(0xFF030A16)
    val cardColor = Color(0xFF091522)
    val primaryCyan = Color(0xFF00E5FF)
    val textWhite = Color(0xFFFFFFFF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Bagian Atas: Logo & Judul
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            // Placeholder untuk Logo (Gunakan Icon atau Image)
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

        // Bagian Tengah: Form Login (Card)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Welcome Back",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textWhite,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(text = "Email / NIM", color = textWhite, fontSize = 12.sp)
                OutlinedTextField(
                    value = emailOrNim,
                    onValueChange = { emailOrNim = it },
                    placeholder = { Text("Enter your email or NIM", color = Color.Gray) },
                    textStyle = TextStyle(color = textWhite), // Mengatur warna teks di sini
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = backgroundColor,
                        unfocusedContainerColor = backgroundColor,
                        focusedBorderColor = primaryCyan,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Password", color = textWhite, fontSize = 12.sp)
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Enter your password", color = Color.Gray) },
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = TextStyle(color = textWhite), // Mengatur warna teks di sini
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = backgroundColor,
                        unfocusedContainerColor = backgroundColor,
                        focusedBorderColor = primaryCyan,
                        unfocusedBorderColor = Color.Gray
                    )
                )
            }
        }

        // Bagian Bawah: Tombol Login & Footer
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onLoginSuccess("student") },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryCyan),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Login as Student", color = backgroundColor, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { onLoginSuccess("admin") },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp
                ),
                shape = RoundedCornerShape(12.dp)
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