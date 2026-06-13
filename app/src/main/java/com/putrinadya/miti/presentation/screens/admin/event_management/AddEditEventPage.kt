package com.putrinadya.miti.presentation.screens.admin.event_management

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.putrinadya.miti.ui.theme.MitiNavy
import com.putrinadya.miti.ui.theme.MitiWhite
import androidx.compose.ui.unit.sp

@Composable
fun AddEditEventPage() {
    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text("Add / Edit Event Page", color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp)
    }
}