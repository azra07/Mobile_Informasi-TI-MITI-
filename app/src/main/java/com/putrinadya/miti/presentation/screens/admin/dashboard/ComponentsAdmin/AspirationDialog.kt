package com.putrinadya.miti.presentation.screens.admin.dashboard.ComponentsAdmin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AspirationDialog(
    onClose: () -> Unit,
    onSend: (content: String, category: String, isAnonymous: Boolean) -> Unit
) {
    var content by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Umum") }
    var isAnonymous by remember { mutableStateOf(false) }
    val categories = listOf("Fasilitas", "Akademik", "Kegiatan", "Umum")
    var expanded by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onClose) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Kirim Aspirasi / Kritik", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)

                // Pilih Kategori
                Box {
                    OutlinedTextField(
                        value = category,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Kategori") },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                        modifier = Modifier.fillMaxWidth().clickable { expanded = true },
                        enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = MaterialTheme.colorScheme.onBackground,
                            disabledTextColor = MaterialTheme.colorScheme.onBackground,
                            disabledLabelColor = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        categories.forEach { cat ->
                            DropdownMenuItem(text = { Text(cat) }, onClick = { category = cat; expanded = false })
                        }
                    }
                }

                // Isi Aspirasi
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    placeholder = { Text("Tulis aspirasimu di sini...", color = MaterialTheme.colorScheme.onBackground) },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onBackground, unfocusedTextColor = MaterialTheme.colorScheme.onBackground)
                )

                // Toggle Anonim
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isAnonymous,
                        onCheckedChange = { isAnonymous = it },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.onBackground)
                    )
                    Text("Kirim sebagai Anonim", color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp)
                }

                Button(
                    onClick = { if(content.isNotBlank()) onSend(content, category, isAnonymous) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground)
                ) {
                    Text("Kirim Sekarang", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}