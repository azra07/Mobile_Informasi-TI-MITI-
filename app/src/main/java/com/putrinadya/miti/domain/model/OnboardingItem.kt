package com.putrinadya.miti.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class OnboardingItem(
    val titleRes: Int,     // Menyimpan ID dari strings.xml (Contoh: R.string.onboard_1)
    val descRes: Int,      // Menyimpan ID dari strings.xml
    val icon: ImageVector, // Menggunakan Material Icons
    val iconBg: Color      // Warna latar belakang ikon
)