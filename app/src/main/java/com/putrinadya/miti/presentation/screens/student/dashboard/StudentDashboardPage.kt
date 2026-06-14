package com.putrinadya.miti.presentation.screens.student.dashboard

import androidx.compose.foundation.Image // Import untuk memuat gambar logo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource // Import untuk memanggil logo drawable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.putrinadya.miti.R
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.presentation.components.FeaturedEventCard
import com.putrinadya.miti.presentation.components.UpcomingActivityCard
import com.putrinadya.miti.presentation.components.RegistrationPopUp
import com.putrinadya.miti.presentation.screens.student.calendar.CalendarPage
import com.putrinadya.miti.presentation.screens.news.NewsPage
import com.putrinadya.miti.presentation.screens.profile.ProfileStudent
import com.putrinadya.miti.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboardPage(viewModel: StudentDashboardViewModel, navController: NavController) {
    val allEvents by viewModel.allEvents.collectAsState()
    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        bottomBar = {
            // PERBAIKAN WARNA BOTTOM BAR: Menggunakan 'surface' agar otomatis berwarna abu-abu lembut di Light Mode (Sangat Seimbang!)
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                val navItems = listOf(
                    Triple(0, Icons.Default.Home, R.string.nav_home),
                    Triple(1, Icons.Default.DateRange, R.string.nav_calendar),
                    Triple(2, Icons.Default.Newspaper, R.string.nav_news),
                    Triple(3, Icons.Default.Person, R.string.nav_profile)
                )

                navItems.forEach { (index, icon, labelRes) ->
                    NavigationBarItem(
                        selected = viewModel.selectedTab == index,
                        onClick = { viewModel.onTabSelected(index) },
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(stringResource(labelRes)) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box( modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(innerPadding)
        ) {
            when (viewModel.selectedTab) {
                0 -> HomeContent(viewModel = viewModel, allEvents = allEvents)
                1 -> CalendarPage(viewModel = hiltViewModel())
                2 -> NewsPage(
                    onNewsClick = { url ->
                        val encodedUrl = java.net.URLEncoder.encode(url, "UTF-8")
                        navController.navigate("news_detail/$encodedUrl")
                    }
                )
                3 -> ProfileStudent(
                    viewModel = viewModel,
                    navController = navController
                )
            }

            viewModel.selectedEventForRegistration?.let { event ->
                val isJoined = viewModel.registeredEvents.any { it.title == event.title }

                RegistrationPopUp(
                    event = event,
                    isJoined = isJoined,
                    onClose = { viewModel.onEventSelected(null) },
                    onJoinClick = {
                        if (isJoined) {
                            viewModel.unregisterForEvent(event)
                        } else {
                            viewModel.registerForEvent(event)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    viewModel: StudentDashboardViewModel,
    allEvents: List<Event>
) {
    val colorScheme = MaterialTheme.colorScheme

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header (LOGO REPLACEMENT, SEBARIS GRETTING & INISIAL DINAMIS 1 HURUF)
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    // 1. Memanggil Gambar logo.jpeg asli Anda menggantikan teks MITI
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo MITI",
                        modifier = Modifier
                            .size(70.dp)
                            .height(20.dp)
                            .padding(bottom = 3.dp)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    // 2. Halo dan Nama Mahasiswa diletakkan sejajar sebaris (Bahasa Indonesia)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Halo, ",
                            style = MaterialTheme.typography.titleMedium,
                            color = colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                        Text(
                            text = "${viewModel.studentProfile?.name ?: "Mahasiswa"}!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onBackground
                        )
                    }
                }

                // 3. Avatar Bulat Dinamis (Menampilkan otomatis 1 huruf pertama nama depan mahasiswa asli di database)
                Box(
                    modifier = Modifier.size(45.dp)
                        .background(colorScheme.primaryContainer, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    val name = viewModel.studentProfile?.name ?: "M"
                    // Mengambil otomatis 1 huruf pertama (e.g. "Putri Fatima" -> "P")
                    val singleInitial = if (name.isNotEmpty()) name.take(1).uppercase() else "M"

                    Text(
                        text = singleInitial,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // Search Bar (PERBAIKAN WARNA KONTRAST DI LIGHT MODE)
        item {
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                placeholder = { Text("Cari kegiatan, workshop...", color = colorScheme.onSurface.copy(alpha = 0.5f)) }, // Bahasa Indonesia
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = colorScheme.onSurface.copy(alpha = 0.6f)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colorScheme.onSurface,
                    unfocusedTextColor = colorScheme.onSurface,
                    focusedContainerColor = colorScheme.surface, // Menggunakan surface kontras tinggi
                    unfocusedContainerColor = colorScheme.surface,
                    focusedBorderColor = colorScheme.primary,
                    unfocusedBorderColor = Color.Transparent
                )
            )
        }

        // Kategori Kegiatan Unggulan (HAPUS TOMBOL "SEE ALL" & FULL BAHASA INDONESIA)
        item {
            Column {
                SectionHeader(title = "Kegiatan Unggulan") // Bahasa Indonesia murni, see all dihapus
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(allEvents.take(3)) { event ->
                        FeaturedEventCard(
                            event = event,
                            onClick = { viewModel.onEventSelected(event) }
                        )
                    }
                }
            }
        }

        // Kategori Kegiatan Mendatang (HAPUS TOMBOL "VIEW ALL" & FULL BAHASA INDONESIA)
        item {
            SectionHeader(title = "Kegiatan Mendatang") // Bahasa Indonesia murni, view all dihapus
        }

        items(viewModel.filteredEvents) { event ->
            UpcomingActivityCard(
                event = event,
                onClick = { viewModel.onEventSelected(event) }
            )
        }
    }
}

// ================= COMPONENT PEMBANTU: SECTION HEADER (SEE ALL & VIEW ALL DIHAPUS) =================
@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}