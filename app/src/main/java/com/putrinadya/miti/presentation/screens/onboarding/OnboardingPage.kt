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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OnboardingPage(
    viewModel: OnboardingViewModel = viewModel(),
    onOnboardingComplete: () -> Unit
) {
    val uiState = viewModel.uiState

    val backgroundColor = Color(0xFF030A16)
    val primaryBlueButton = Color(0xFF0F3D8C) // Biru pekat sesuai mockup Anda
    val iconCyanBg = Color(0xFF13B5B1) // Tosca cerah untuk slide 2
    val iconBlueBg = Color(0xFF0F3D8C) // Biru pekat untuk slide 1 dan 3
    val textWhite = Color(0xFFFFFFFF)

    LaunchedEffect(uiState.isCompleted) {
        if (uiState.isCompleted) {
            onOnboardingComplete()
        }
    }

    val titles = listOf(
        "Semua Info Terpusat",
        "Daftar Lebih Mudah",
        "Pantau Portofolio"
    )

    val descriptions = listOf(
        "Temukan informasi seminar,\nlomba, dan kegiatan Prodi TI\ntanpa takut tertinggal jarkoman.",
        "Ikuti kegiatan yang kamu\nminati hanya dengan satu klik\nlangsung dari dalam aplikasi.",
        "Riwayat keaktifanmu akan\ntercatat otomatis di profil\nsebagai portofolio digital."
    )

    // Menggunakan material icons yang mirip dengan figma Anda
    val icons = listOf(
        Icons.Default.Notifications,  // Ikon Lonceng
        Icons.Default.BusinessCenter, // Ikon Tas Kerja (Koper)
        Icons.Default.BarChart        // Ikon Grafik Analitik
    )

    val iconBackgrounds = listOf(iconBlueBg, iconCyanBg, iconBlueBg)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Bagian Tengah: Slide Konten (Icon, Title, Description)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon Box Rounded Sesuai Figma
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
                    tint = textWhite,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Title Slide
            Text(
                text = titles[uiState.currentPage],
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = textWhite,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description Slide dengan lineHeight renggang yang nyaman dibaca
            Text(
                text = descriptions[uiState.currentPage],
                fontSize = 15.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Bagian Bawah: Indikator Dots & Tombol Navigasi
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // PIL INDICATOR (Indikator melebar saat aktif) sesuai mockup Anda
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 0 until viewModel.totalPages) {
                    val isActive = i == uiState.currentPage
                    Box(
                        modifier = Modifier
                            .height(6.dp)
                            .width(if (isActive) 24.dp else 6.dp) // Lebih panjang jika aktif
                            .background(
                                color = if (isActive) primaryBlueButton else Color.Gray.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    )
                }
            }

            // Row Aksi Bottom: [Skip] ------------ [Next / Mulai Sekarang ›]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tombol Skip di Kiri
                if (uiState.currentPage < viewModel.totalPages - 1) {
                    Text(
                        text = "Skip",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier
                            .clickable { viewModel.onSkip() }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.width(50.dp))
                }

                // Tombol Next / Mulai Sekarang Bulat di Kanan
                Button(
                    onClick = { viewModel.onNextPage() },
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .height(48.dp)
                        .width(if (uiState.currentPage == viewModel.totalPages - 1) 180.dp else 140.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryBlueButton),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (uiState.currentPage == viewModel.totalPages - 1) "Mulai Sekarang" else "Next",
                            color = textWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "›",
                            color = textWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}