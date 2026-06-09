package com.putrinadya.miti.presentation.screens.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.putrinadya.miti.presentation.components.TopBar
import com.putrinadya.miti.presentation.components.MitiInputField
import com.putrinadya.miti.R
import com.putrinadya.miti.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(
    viewModel: RegisterViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val uiState = viewModel.uiState
    var roleExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val roleOptions = listOf(
        "student" to R.string.role_student,
        "admin" to R.string.role_admin
    )

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onRegisterSuccess()
            viewModel.resetSuccessState()
        }
    }

    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.create_account),
                onBackClick = onBackClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
           // Header
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.register_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.register_subtitle),
                    fontSize = 14.sp,
                    color = MitiGray
                )
            }

            // Card Form Input
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f, fill = false).padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Error Message
                    if (uiState.error != null) {
                        Text(text = uiState.error, color = Color.Red, fontSize = 12.sp)
                    }

                    MitiInputField(
                        label = stringResource(R.string.full_name),
                        value = uiState.name,
                        onValueChange = { viewModel.onNameChanged(it) },
                        placeholder = "Putri Nadya"
                    )

                    MitiInputField(
                        label = stringResource(R.string.email_label),
                        value = uiState.email,
                        onValueChange = { viewModel.onEmailChanged(it) },
                        placeholder = "2024123456789@mhs.ulm.ac.id"
                    )

                    MitiInputField(
                        label = stringResource(R.string.nim_label),
                        value = uiState.nim,
                        onValueChange = { viewModel.onNimChanged(it) },
                        placeholder = "2024123456789"
                    )

                    MitiInputField(
                        label = stringResource(R.string.password_label),
                        value = uiState.password,
                        onValueChange = { viewModel.onPasswordChanged(it) },
                        placeholder = "********",
                        isPassword = true
                    )

                    // Role Dropdown
                    Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                        Text(
                            stringResource(R.string.register_as),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        ExposedDropdownMenuBox(
                            expanded = roleExpanded,
                            onExpandedChange = { roleExpanded = !roleExpanded }
                        ) {
                            val selectedRoleLabel = roleOptions.find { it.first == uiState.role }?.second ?: R.string.role_student

                            OutlinedTextField(
                                value = stringResource(id = selectedRoleLabel),
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = roleExpanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.background,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.background
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = roleExpanded,
                                onDismissRequest = { roleExpanded = false }
                            ) {
                                roleOptions.forEach { (optionValue, optionLabelRes) ->
                                    DropdownMenuItem(
                                        text = { Text(stringResource(id = optionLabelRes)) },
                                        onClick = {
                                            viewModel.onRoleChanged(optionValue)
                                            roleExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Button Create Account
                    Button(
                        onClick = { viewModel.register() },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(25.dp),
                        enabled = !uiState.isLoading
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.btn_create_account),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}