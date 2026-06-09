package com.putrinadya.miti.presentation.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.putrinadya.miti.domain.model.OnboardingItem
import com.putrinadya.miti.R

@Composable
fun OnboardingPage(
    viewModel: OnboardingViewModel = viewModel(),
    onOnboardingComplete: () -> Unit
) {
    val uiState = viewModel.uiState
    val currentPageData = viewModel.onboardPages[uiState.currentPage]

    LaunchedEffect(uiState.isCompleted) {
        if (uiState.isCompleted) {
            onOnboardingComplete()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OnboardingContent(item = currentPageData)
        }

        OnboardingFooter(
            currentPage = uiState.currentPage,
            totalPages = viewModel.totalPages,
            onNext = viewModel::onNextPage,
            onSkip = viewModel::onSkip
        )
    }
}

@Composable
fun OnboardingContent(item: OnboardingItem) {
    Box(
        modifier = Modifier
            .size(160.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(item.iconBg),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            item.icon,
            null,
            tint = Color.White,
            modifier = Modifier.size(64.dp)
        )
    }
    Spacer(modifier = Modifier.height(48.dp))
    Text(
        stringResource(item.titleRes),
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        stringResource(item.descRes),
        fontSize = 15.sp,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
        textAlign = TextAlign.Center,
        lineHeight = 22.sp
    )
}

@Composable
fun OnboardingFooter(currentPage: Int, totalPages: Int, onNext: () -> Unit, onSkip: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(totalPages) { index ->
                val isActive = index == currentPage
                Box(
                    modifier = Modifier
                        .height(6.dp)
                        .width(if (isActive) 24.dp else 6.dp)
                        .background(
                            if (isActive) MaterialTheme.colorScheme.primary else Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        //Button Row
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentPage < totalPages - 1) {
                Text(stringResource(id = R.string.btn_skip),
                    color = Color.Gray,
                    modifier = Modifier.clickable { onSkip() }
                )
            } else {
                Spacer(Modifier.width(40.dp))
            }

            Button(
                onClick = onNext,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    if (currentPage == totalPages - 1)
                        stringResource(id = R.string.btn_start)
                        else stringResource(id = R.string.btn_next)
                )
            }
        }
    }
}