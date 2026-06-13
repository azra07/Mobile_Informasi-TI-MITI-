package com.putrinadya.miti.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.ui.theme.* // Mengimpor tema warna kustom Anda

@Composable
fun RegistrationPopUp(
    event: Event,
    isJoined: Boolean,
    onClose: () -> Unit,
    onJoinClick: () -> Unit
) {
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
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.onBackground)
                        .padding(20.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = event.category,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f), // Menggunakan MitiWhite
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                                        RoundedCornerShape(6.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                                        CircleShape
                                    )
                                    .clickable { onClose() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = event.title,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        RowInfoBox(
                            title = "DATE",
                            value = "${event.dayMonth} ${event.year}, 2026",
                            icon = "📅",
                            modifier = Modifier.weight(1f)
                        )
                        RowInfoBox(
                            title = "TIME",
                            value = event.time,
                            icon = "🕒",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    RowInfoBox(
                        title = "LOCATION",
                        value = event.location,
                        icon = "📍",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.onBackground,
                                RoundedCornerShape(12.dp)
                            )
                            .background(MaterialTheme.colorScheme.onBackground)
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("👥", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Available Spots",
                                    color = MaterialTheme.colorScheme.onBackground, // Menggunakan MitiWhite
                                    fontSize = 14.sp
                                )
                            }
                            Text(
                                "8 / 50",
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Text(
                        "Description",
                        color = MaterialTheme.colorScheme.onBackground, // Menggunakan MitiWhite
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Learn the fundamentals of user interface and experience design with hands-on projects.",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onJoinClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isJoined) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground
                        ),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text(
                            text = if (isJoined) "Joined / Registered ✓" else "Join Activity",
                            color = if (isJoined) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RowInfoBox(title: String, value: String, icon: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.onBackground,
                RoundedCornerShape(12.dp)
            )
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(icon, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(title, color = MaterialTheme.colorScheme.onBackground, fontSize = 10.sp)
                Text(
                    value,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}