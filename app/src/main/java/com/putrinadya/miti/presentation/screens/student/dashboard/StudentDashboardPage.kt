package com.putrinadya.miti.presentation.screens.student.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
            NavigationBar(
                containerColor = Color(0xFF040F1F),
                tonalElevation = (8.dp)
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
                            selectedIconColor = MitiNavy,
                            selectedTextColor = MitiCyan,
                            indicatorColor = MitiCyan,
                            unselectedIconColor = MitiGray,
                            unselectedTextColor = MitiGray
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
                3 -> ProfileStudent(viewModel = hiltViewModel())
            }

            // POP-UP PENDAFTARAN (DIALOG)
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
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        stringResource(R.string.greeting_evening),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorScheme.onSurfaceVariant
                    )
                    Text(
                        "Alex!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onBackground
                    )
                }
                // Avatar
                Box(
                    modifier = Modifier.size(45.dp)
                        .background(colorScheme.primaryContainer, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "AJ",
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        item {
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                placeholder = { Text(stringResource(R.string.search_placeholder)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surfaceVariant,
                    unfocusedContainerColor = colorScheme.surfaceVariant,
                    focusedBorderColor = colorScheme.primary,
                    unfocusedBorderColor = Color.Transparent
                )
            )
        }

        item {
            Column {
                SectionHeader(
                    title = stringResource(R.string.section_featured),
                    actionText = stringResource(R.string.label_see_all)
                )
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

        item {
            SectionHeader(
                title = stringResource(R.string.section_upcoming),
                actionText = stringResource(R.string.label_view_all)
            )
        }

        items(viewModel.filteredEvents) { event ->
            UpcomingActivityCard(
                event = event,
                onClick = { viewModel.onEventSelected(event) }
            )
        }
    }
}

@Composable
fun SectionHeader(title: String, actionText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = actionText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}