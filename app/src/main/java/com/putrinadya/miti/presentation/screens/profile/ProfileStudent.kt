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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.putrinadya.miti.R
import com.putrinadya.miti.presentation.MainViewModel
import com.putrinadya.miti.presentation.navigation.Screen
import com.putrinadya.miti.presentation.screens.admin.dashboard.AspirationDialog
import com.putrinadya.miti.presentation.screens.student.dashboard.StudentDashboardViewModel
import com.putrinadya.miti.ui.theme.*

@Composable
fun ProfileStudent(
    viewModel: StudentDashboardViewModel,
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val user = viewModel.studentProfile
    var showEditDialog by remember { mutableStateOf(false) }
    val isDarkMode by mainViewModel.isDarkMode.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = stringResource(id = R.string.nav_profile),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MitiWhite,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(MaterialTheme.colorScheme.primary)
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
                                .border(4.dp, MaterialTheme.colorScheme.surface, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            val initial = if (!user?.name.isNullOrEmpty()) user?.name?.take(1)?.uppercase() else "U"
                            Text(initial ?: "", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.background, fontSize = 24.sp)
                        }

                        OutlinedButton(
                            onClick = { showEditDialog = true },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 8.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary)
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(stringResource(id = R.string.edit_profile), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 20.dp)
                    ) {
                        Text(user?.name ?: "", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MitiWhite)
                        Spacer(modifier = Modifier.height(12.dp))

                        ProfileDetailRow(icon = "👤", label = stringResource(id = R.string.nim_label) ?: "-\"}")
                        ProfileDetailRow(icon = "✉️", label = user?.email?: "-")
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.onShowAspirationDialog(true) },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.1f),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("💡", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(stringResource(id = R.string.aspiration_title), color = MitiWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(stringResource(id = R.string.aspiration_subtitle), color = MitiWhite, fontSize = 11.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.KeyboardArrowRight, null, tint = MaterialTheme.colorScheme.onBackground)
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
                        Text("Total Activities Joined:", fontSize = 14.sp, color = MitiWhite, fontWeight = FontWeight.Bold)
                    }
                    Text(viewModel.registeredEvents.size.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }

        item {
            Text("Registered Activities", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MitiWhite)
        }

        if (viewModel.registeredEvents.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No registered activities yet.", color = MitiGray, fontSize = 14.sp)
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)) {
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
                                Text("Dark Mode", fontSize = 14.sp, color = MitiWhite, fontWeight = FontWeight.Bold)
                                Text(if (isDarkMode) "On" else "Off", fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground)
                            }
                        }
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { mainViewModel.toggleTheme(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.background,
                                checkedTrackColor = MaterialTheme.colorScheme.primary
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
                                Text("Language", fontSize = 14.sp, color = MitiWhite, fontWeight = FontWeight.Bold)
                                Text("English", fontSize = 11.sp, color = MitiGray)
                            }
                        }
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Go", tint = MaterialTheme.colorScheme.onBackground)
                    }

                    Divider(color = Color(0xFF142233), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.sendPasswordReset() } // Pemicu kirim email
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🔐", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("Keamanan Akun", fontSize = 14.sp, color = MitiWhite, fontWeight = FontWeight.Bold)
                                Text("Kirim link ubah password ke email", fontSize = 11.sp, color = MitiWhite)
                            }
                        }
                    }
                }
            }
        }

        item {
            OutlinedButton(
                onClick = {
                    viewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.StudentDashboard.route) { inclusive = true }
                    }
                },
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
                    Text(stringResource(id = R.string.logout), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }

    if (showEditDialog && user != null) {
        EditProfileDialog(
            currentName = user.name,
            currentNim = user.nim,
            currentEmail = user.email,
            onClose = { showEditDialog = false },
            onSave = { name, nim, email ->
                viewModel.updateProfile(name, nim, email)
                showEditDialog = false
            }
        )
    }

    if (viewModel.passwordResetMessage != null) {
        AlertDialog(
            onDismissRequest = { viewModel.clearPasswordMessage() },
            title = { Text("Ubah Password", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold) },
            text = { Text(viewModel.passwordResetMessage!!, color = MaterialTheme.colorScheme.onBackground) },
            confirmButton = {
                TextButton(onClick = { viewModel.clearPasswordMessage() }) {
                    Text("Mengerti", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp)
        )
    }

    if (viewModel.showAspirationDialog) {
        AspirationDialog(
            onClose = { viewModel.onShowAspirationDialog(false) },
            onSend = { content, category, anonymous ->
                viewModel.sendAspiration(content, category, anonymous)
            }
        )
    }
}

// ================= COMPONENT PEMBANTU: DETAIL ROW =================
@Composable
private fun ProfileDetailRow(icon: String, label: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, fontSize = 13.sp, color = MitiGray)
    }
}

// ================= COMPONENT PEMBANTU: DIALOG EDIT PROFILE MAHASISWA =================
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

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(id = R.string.edit_profile), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                MitiWhite.copy(alpha = 0.1f),
                                CircleShape
                            )
                            .clickable { onClose() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = MitiWhite, modifier = Modifier.size(16.dp))
                    }
                }

                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("AJ", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.background, fontSize = 28.sp)
                    }
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Change Photo", tint = MaterialTheme.colorScheme.background, modifier = Modifier.size(16.dp))
                    }
                }
                Text(stringResource(id = R.string.edit_profile), fontSize = 12.sp, color = MitiGray)

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(id = R.string.full_name), color = MitiWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MitiWhite,
                            unfocusedTextColor = MitiWhite,
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(id = R.string.nim_label), color = MitiWhite, fontSize = 12.sp)
                    OutlinedTextField(
                        value = nimInput,
                        onValueChange = { nimInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MitiWhite,
                            unfocusedTextColor = MitiWhite,
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(id = R.string.email_label), color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp)
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MitiWhite,
                            unfocusedTextColor = MitiWhite,
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
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
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF142233)),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Text(stringResource(id = R.string.btn_cancel), color = MitiWhite, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { onSave(nameInput, nimInput, emailInput) },
                        modifier = Modifier
                            .weight(1.3f)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Check, contentDescription = "Save", tint = MitiWhite, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(stringResource(id = R.string.btn_save), color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// ================= COMPONENT PEMBANTU: REGISTERED ACTIVITY CARD =================
@Composable
fun RegisteredActivityCard(event: com.putrinadya.miti.domain.model.Event) {
     val categoryColor = try {
        Color(android.graphics.Color.parseColor(event.categoryColorHex))
    } catch (e: Exception) {
         MitiGray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .height(80.dp)
                    .background(categoryColor)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(event.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MitiWhite)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(event.category, fontSize = 11.sp, color = MitiGray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🕒 ${event.dayMonth} ${event.year}", fontSize = 11.sp, color = MitiGray)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("📍 ${event.location}", fontSize = 11.sp, color = MitiGray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }

                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(stringResource(id = R.string.event_joined), fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}