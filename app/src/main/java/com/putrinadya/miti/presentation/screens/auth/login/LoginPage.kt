//package com.putrinadya.miti.presentation.screens.auth.login
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun LoginPage(
//    onLoginSuccess: (role: String) -> Unit
//) {
//    var emailOrNim by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    // Warna tema gelap sesuai mockup (Dark Navy & Tosca/Cyan)
//    val backgroundColor = Color(0xFF030A16)
//    val cardColor = Color(0xFF091522)
//    val primaryCyan = Color(0xFF00E5FF)
//    val textWhite = Color(0xFFFFFFFF)
//
//    Column(
//        modifier = Modifier.Companion
//            .fillMaxSize()
//            .background(backgroundColor)
//            .padding(24.dp),
//        horizontalAlignment = Alignment.Companion.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//        // Bagian Atas: Logo & Judul
//        Column(
//            horizontalAlignment = Alignment.Companion.CenterHorizontally,
//            modifier = Modifier.Companion.padding(top = 40.dp)
//        ) {
//            // Placeholder untuk Logo (Gunakan Icon atau Image)
//            Box(
//                modifier = Modifier.Companion
//                    .size(80.dp)
//                    .background(primaryCyan, shape = RoundedCornerShape(16.dp)),
//                contentAlignment = Alignment.Companion.Center
//            ) {
//                Text(text = "✦", fontSize = 36.sp, color = backgroundColor)
//            }
//            Spacer(modifier = Modifier.Companion.height(16.dp))
//            Text(
//                text = "IT Activity Hub",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Companion.Bold,
//                color = textWhite
//            )
//            Text(
//                text = "Manage your university tech events",
//                fontSize = 14.sp,
//                color = Color.Companion.Gray
//            )
//        }
//
//        // Bagian Tengah: Form Login (Card)
//        Card(
//            modifier = Modifier.Companion.fillMaxWidth(),
//            colors = CardDefaults.cardColors(containerColor = cardColor),
//            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
//        ) {
//            Column(
//                modifier = Modifier.Companion.padding(20.dp)
//            ) {
//                Text(
//                    text = "Welcome Back",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Companion.Bold,
//                    color = textWhite,
//                    modifier = Modifier.Companion.padding(bottom = 16.dp)
//                )
//
//                Text(text = "Email / NIM", color = textWhite, fontSize = 12.sp)
//                OutlinedTextField(
//                    value = emailOrNim,
//                    onValueChange = { emailOrNim = it },
//                    placeholder = { Text("Enter your email or NIM", color = Color.Companion.Gray) },
//                    textStyle = TextStyle(color = textWhite), // Mengatur warna teks di sini
//                    modifier = Modifier.Companion.fillMaxWidth().padding(vertical = 8.dp),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedContainerColor = backgroundColor,
//                        unfocusedContainerColor = backgroundColor,
//                        focusedBorderColor = primaryCyan,
//                        unfocusedBorderColor = Color.Companion.Gray
//                    )
//                )
//
//                Spacer(modifier = Modifier.Companion.height(8.dp))
//
//                Text(text = "Password", color = textWhite, fontSize = 12.sp)
//                OutlinedTextField(
//                    value = password,
//                    onValueChange = { password = it },
//                    placeholder = { Text("Enter your password", color = Color.Companion.Gray) },
//                    visualTransformation = PasswordVisualTransformation(),
//                    textStyle = TextStyle(color = textWhite), // Mengatur warna teks di sini
//                    modifier = Modifier.Companion.fillMaxWidth().padding(vertical = 8.dp),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedContainerColor = backgroundColor,
//                        unfocusedContainerColor = backgroundColor,
//                        focusedBorderColor = primaryCyan,
//                        unfocusedBorderColor = Color.Companion.Gray
//                    )
//                )
//            }
//        }
//
//        // Bagian Bawah: Tombol Login & Footer
//        Column(
//            modifier = Modifier.Companion.fillMaxWidth(),
//            horizontalAlignment = Alignment.Companion.CenterHorizontally
//        ) {
//            Button(
//                onClick = { onLoginSuccess("student") },
//                modifier = Modifier.Companion.fillMaxWidth().height(50.dp),
//                colors = ButtonDefaults.buttonColors(containerColor = primaryCyan),
//                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
//            ) {
//                Text(
//                    text = "Login as Student",
//                    color = backgroundColor,
//                    fontWeight = FontWeight.Companion.Bold
//                )
//            }
//
//            Spacer(modifier = Modifier.Companion.height(12.dp))
//
//            OutlinedButton(
//                onClick = { onLoginSuccess("admin") },
//                modifier = Modifier.Companion.fillMaxWidth().height(50.dp),
//                border = ButtonDefaults.outlinedButtonBorder.copy(
//                    width = 1.dp
//                ),
//                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
//            ) {
//                Text(
//                    text = "Login as Admin / Lecturer",
//                    color = primaryCyan,
//                    fontWeight = FontWeight.Companion.Bold
//                )
//            }
//
//            Spacer(modifier = Modifier.Companion.height(24.dp))
//            Text(
//                text = "Teknologi Informasi - University Lambung Mangkurat",
//                fontSize = 11.sp,
//                color = Color.Companion.Gray
//            )
//        }
//    }
//}