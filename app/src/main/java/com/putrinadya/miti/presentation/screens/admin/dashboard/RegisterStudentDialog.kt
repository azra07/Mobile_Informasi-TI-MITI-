package com.putrinadya.miti.presentation.screens.admin.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.putrinadya.miti.ui.theme.*

@Composable
fun RegisterStudentDialog(
    onClose: () -> Unit,
    onRegister: (String, String, String, String) -> Unit,
    isLoading: Boolean
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onClose) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Daftarkan Mahasiswa Baru", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)

                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nama Lengkap") })
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                OutlinedTextField(value = nim, onValueChange = { nim = it }, label = { Text("NIM") })
                OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password Default") })

                Button(
                    onClick = { onRegister(name, email, password, nim) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    if (isLoading) CircularProgressIndicator(modifier = Modifier.size (20.dp))
                    else Text("Daftarkan & Simpan", color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }
    }
}