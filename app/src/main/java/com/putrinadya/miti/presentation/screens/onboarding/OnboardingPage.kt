package com.putrinadya.miti.presentation.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.putrinadya.miti.R
import com.putrinadya.miti.ui.theme.*

@Composable
fun OnboardingPage(
    viewModel: OnboardingViewModel = viewModel(),
    onOnboardingComplete: () -> Unit
) {
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isCompleted) {
        if (uiState.isCompleted) {
            onOnboardingComplete()
        }
    }

    // Mengambil string dari strings.xml secara dinamis
    val titles = listOf(
        stringResource(R.string.onboarding_title_1),
        stringResource(R.string.onboarding_title_2),
        stringResource(R.string.onboarding_title_3)
    )

    val descriptions = listOf(
        stringResource(R.string.onboarding_desc_1),
        stringResource(R.string.onboarding_desc_2),
        stringResource(R.string.onboarding_desc_3)
    )

    val icons = listOf(
        Icons.Default.Notifications,
        Icons.Default.BusinessCenter,
        Icons.Default.BarChart
    )

    // Menggunakan variabel warna terpusat dari Color.kt Anda
    val iconBackgrounds = listOf(MitiSecondary, MitiAccent, MitiSecondary)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MitiNavy)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(iconBackgrounds[uiState.currentPage]),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icons[uiState.currentPage],
                    contentDescription = "Slide Icon",
                    tint = MitiWhite,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = titles[uiState.currentPage],
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MitiWhite,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = descriptions[uiState.currentPage],
                fontSize = 15.sp,
                color = MitiGray,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 0 until viewModel.totalPages) {
                    val isActive = i == uiState.currentPage
                    Box(
                        modifier = Modifier
                            .height(6.dp)
                            .width(if (isActive) 24.dp else 6.dp)
                            .background(
                                color = if (isActive) MitiSecondary else MitiGray.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (uiState.currentPage < viewModel.totalPages - 1) {
                    Text(
                        text = stringResource(R.string.btn_skip),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MitiGray,
                        modifier = Modifier
                            .clickable { viewModel.onSkip() }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.width(50.dp))
                }

                Button(
                    onClick = { viewModel.onNextPage() },
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .height(48.dp)
                        .width(if (uiState.currentPage == viewModel.totalPages - 1) 180.dp else 140.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MitiSecondary),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (uiState.currentPage == viewModel.totalPages - 1) {
                                stringResource(R.string.btn_start)
                            } else {
                                stringResource(R.string.btn_next)
                            },
                            color = MitiWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "›",
                            color = MitiWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}