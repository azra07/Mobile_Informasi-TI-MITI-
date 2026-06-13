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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import com.putrinadya.miti.R
import com.putrinadya.miti.ui.theme.*

@Composable
fun OnboardingPage(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onOnboardingComplete: () -> Unit
) {
    val uiState = viewModel.uiState
    val pagerState = rememberPagerState(pageCount = { viewModel.totalPages })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        viewModel.onPageSwiped(pagerState.currentPage)
    }

    LaunchedEffect(uiState.isCompleted) {
        if (uiState.isCompleted) {
            onOnboardingComplete()
        }
    }

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

    // Mengembalikan Ikon Asli Anda 100%
    val icons = listOf(
        Icons.Default.Notifications,
        Icons.Default.BusinessCenter,
        Icons.Default.BarChart
    )

    val iconBackgrounds = listOf(MitiSecondary, MitiAccent, MitiSecondary)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // MEMASUKKAN KONTEN COLUMN KE DALAM PAGER AGAR DINAMIS SAAT DI-SWIPE & PRESISI DI TENGAH
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) { pageIndex ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Memusatkan konten tepat di tengah layar secara vertikal
            ) {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(iconBackgrounds[pageIndex]),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icons[pageIndex],
                        contentDescription = "Slide Icon",
                        tint = MitiWhite,
                        modifier = Modifier.size(64.dp)
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = titles[pageIndex],
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground, // Dinamis: Berubah Hitam di Light Mode
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = descriptions[pageIndex],
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f), // Dinamis: Berubah kontras di Light Mode
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        // BAGIAN BAWAH: Indikator & Tombol Navigasi
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Indikator Titik Tiga
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 0 until viewModel.totalPages) {
                    val isActive = i == pagerState.currentPage
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

            // Tombol Navigasi
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (pagerState.currentPage < viewModel.totalPages - 1) {
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
                    onClick = {
                        if (pagerState.currentPage == viewModel.totalPages - 1) {
                            viewModel.onSkip() // Menyimpan status selesai ke local storage
                            onOnboardingComplete() // PERBAIKAN: Menjamin navigasi langsung berfungsi ke halaman Login
                        } else {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .height(48.dp)
                        .width(if (pagerState.currentPage == viewModel.totalPages - 1) 180.dp else 140.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MitiSecondary),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (pagerState.currentPage == viewModel.totalPages - 1) {
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