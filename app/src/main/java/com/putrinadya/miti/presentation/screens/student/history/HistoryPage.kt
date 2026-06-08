package com.putrinadya.miti.presentation.screens.student.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.putrinadya.miti.presentation.components.HistoryItem
import com.putrinadya.miti.presentation.components.TopBar

@Composable
fun HistoryPage(
    viewModel: HistoryViewModel = viewModel()
) {
    val uiState = viewModel.uiState
    val backgroundColor = Color(0xFF030A16)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Memanggil TopBar global yang baru Anda buat
        TopBar(title = "Activity History")

        if (uiState.pastEvents.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No past activities found.",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(uiState.pastEvents) { event ->
                    // Memanggil HistoryItem global yang baru Anda buat
                    HistoryItem(event = event)
                }
            }
        }
    }
}