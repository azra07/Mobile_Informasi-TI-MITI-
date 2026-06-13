package com.putrinadya.miti.presentation.screens.student.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.putrinadya.miti.presentation.components.HistoryItem
import com.putrinadya.miti.presentation.components.TopBar
import com.putrinadya.miti.R
import com.putrinadya.miti.ui.theme.MitiGray

@Composable
fun HistoryPage(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBar(title = stringResource(id = R.string.history))

        if (uiState.pastEvents.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.no_history),
                    color = MitiGray,
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
                    HistoryItem(event = event)
                }
            }
        }
    }
}