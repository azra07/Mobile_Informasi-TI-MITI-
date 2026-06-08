package com.putrinadya.miti.presentation.screens.admin.event_management

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun AddEditEventPage() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF030A16)),
        contentAlignment = Alignment.Center
    ) {
        Text("Add / Edit Event Page", color = Color.White, fontSize = 18.sp)
    }
}