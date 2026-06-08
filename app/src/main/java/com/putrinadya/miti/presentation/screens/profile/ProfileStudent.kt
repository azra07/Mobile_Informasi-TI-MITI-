package com.putrinadya.miti.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.putrinadya.miti.presentation.screens.student.dashboard.StudentDashboardViewModel
import com.putrinadya.miti.presentation.screens.student.dashboard.ProfileDetailRow
import com.putrinadya.miti.presentation.screens.student.dashboard.RegisteredActivityCard

@Composable
fun ProfileStudent(viewModel: StudentDashboardViewModel) {
    var studentName by remember { mutableStateOf("Alex Johnson") }
    var studentNim by remember { mutableStateOf("2024123456") }
    var studentEmail by remember { mutableStateOf("alex.johnson@university.edu") }

    var showEditDialog by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(true) }

    val backgroundColor = Color(0xFF030A16)
    val cardColor = Color(0xFF091522)
    val primaryCyan = Color(0xFF00E5FF)
    val textWhite = Color(0xFFFFFFFF)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textWhite,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(primaryCyan)
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
                                .background(Color(0xFF00B0FF), CircleShape)
                                .border(4.dp, cardColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("AJ", fontWeight = FontWeight.Bold, color = backgroundColor, fontSize = 24.sp)
                        }

                        OutlinedButton(
                            onClick = { showEditDialog = true },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 8.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = primaryCyan),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = androidx.compose.ui.graphics.SolidColor(primaryCyan)
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Edit Profile", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 20.dp)
                    ) {
                        Text(studentName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = textWhite)
                        Spacer(modifier = Modifier.height(12.dp))

                        ProfileDetailRow(icon = "👤", label = "NIM (Student ID): $studentNim")
                        ProfileDetailRow(icon = "🎓", label = "IT Student")
                        ProfileDetailRow(icon = "✉️", label = studentEmail)
                        ProfileDetailRow(icon = "🏛️", label = "Computer Science")
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(0xFF142233), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("📅", fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Total Activities Joined:", fontSize = 14.sp, color = textWhite, fontWeight = FontWeight.Bold)
                    }
                    Text(viewModel.registeredEvents.size.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = primaryCyan)
                }
            }
        }

        item {
            Text("Registered Activities", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textWhite)
        }

        if (viewModel.registeredEvents.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No registered activities yet.", color = Color.Gray, fontSize = 14.sp)
                }
            }
        } else {
            items(viewModel.registeredEvents) { event ->
                RegisteredActivityCard(event = event)
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
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
                                Text("Dark Mode", fontSize = 14.sp, color = textWhite, fontWeight = FontWeight.Bold)
                                Text(if (isDarkMode) "On" else "Off", fontSize = 11.sp, color = Color.Gray)
                            }
                        }
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { isDarkMode = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = backgroundColor,
                                checkedTrackColor = primaryCyan
                            )
                        )
                    }

                    Divider(color = Color(0xFF142233), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🌐", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("Language", fontSize = 14.sp, color = textWhite, fontWeight = FontWeight.Bold)
                                Text("English", fontSize = 11.sp, color = Color.Gray)
                            }
                        }
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Go", tint = Color.Gray)
                    }
                }
            }
        }

        item {
            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF3B30)),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF2E0C0C))
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ExitToApp, contentDescription = "Logout", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Logout", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }

    if (showEditDialog) {
        EditProfileDialog(
            currentName = studentName,
            currentNim = studentNim,
            currentEmail = studentEmail,
            onClose = { showEditDialog = false },
            onSave = { name, nim, email ->
                studentName = name
                studentNim = nim
                studentEmail = email
                showEditDialog = false
            }
        )
    }
}

// ================= DIALOG: POP-UP EDIT PROFILE MAHASISWA =================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    currentName: String,
    currentNim: String,
    currentEmail: String,
    onClose: () -> Unit,
    onSave: (name: String, nim: String, email: String) -> Unit
) {
    var nameInput by remember { mutableStateOf(currentName) }
    var nimInput by remember { mutableStateOf(currentNim) }
    var emailInput by remember { mutableStateOf(currentEmail) }

    val backgroundColor = Color(0xFF030A16)
    val cardColor = Color(0xFF091522)
    val primaryCyan = Color(0xFF00E5FF)
    val textWhite = Color(0xFFFFFFFF)

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(24.dp).wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF05111E)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Edit Profile", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textWhite)
                    Box(
                        modifier = Modifier.size(32.dp).background(Color.White.copy(alpha = 0.1f), CircleShape).clickable { onClose() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }

                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier.size(90.dp).background(primaryCyan, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("AJ", fontWeight = FontWeight.Bold, color = backgroundColor, fontSize = 28.sp)
                    }
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(primaryCyan, CircleShape)
                            .border(2.dp, Color(0xFF05111E), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Change Photo", tint = backgroundColor, modifier = Modifier.size(16.dp))
                    }
                }
                Text("Tap to change photo", fontSize = 12.sp, color = Color.Gray)

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Full Name", color = textWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textWhite,
                            unfocusedTextColor = textWhite,
                            focusedContainerColor = cardColor,
                            unfocusedContainerColor = cardColor,
                            focusedBorderColor = primaryCyan,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("NIM (Student ID)", color = textWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = nimInput,
                        onValueChange = { nimInput = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textWhite,
                            unfocusedTextColor = textWhite,
                            focusedContainerColor = cardColor,
                            unfocusedContainerColor = cardColor,
                            focusedBorderColor = primaryCyan,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Email Address", color = textWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textWhite,
                            unfocusedTextColor = textWhite,
                            focusedContainerColor = cardColor,
                            unfocusedContainerColor = cardColor,
                            focusedBorderColor = primaryCyan,
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
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF142233)),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Text("Cancel", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { onSave(nameInput, nimInput, emailInput) },
                        modifier = Modifier.weight(1.3f).height(45.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryCyan),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Check, contentDescription = "Save", tint = backgroundColor, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Save Changes", color = backgroundColor, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}