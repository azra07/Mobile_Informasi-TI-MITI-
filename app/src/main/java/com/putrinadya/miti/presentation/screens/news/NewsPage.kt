package com.putrinadya.miti.presentation.screens.news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.putrinadya.miti.R
import coil.compose.AsyncImage
import com.putrinadya.miti.ui.theme.MitiGray
import com.putrinadya.miti.ui.theme.MitiWhite

@Composable
fun NewsPage(onNewsClick: (String) -> Unit, viewModel: NewsViewModel = hiltViewModel()) {
    val state = viewModel.uiState

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        // PERBAIKAN: Judul halaman otomatis hitam di Light Mode
        Text(
            text = stringResource(id = R.string.news_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground, // Dinamis
            modifier = Modifier.padding(16.dp)
        )

        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        else if (state.error != null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "${stringResource(id = R.string.error_loading)}: ${state.error}",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else {
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(state.newsList) { news ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onNewsClick(news.url)
                            },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // Latar belakang abu terang di Light Mode
                    ) {
                        Column {
                            AsyncImage(
                                model = news.imageUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth().height(180.dp),
                                contentScale = ContentScale.Crop
                            )
                            Column(Modifier.padding(12.dp)) {
                                // Sumber Berita: Menggunakan primary (Teal/Cyan)
                                Text(
                                    text = news.source,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.height(4.dp))
                                // PERBAIKAN: Judul artikel berita otomatis hitam di Light Mode
                                Text(
                                    text = news.title,
                                    color = MaterialTheme.colorScheme.onSurface, // Dinamis
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 2
                                )
                                Spacer(Modifier.height(6.dp))
                                // PERBAIKAN: Deskripsi artikel berita otomatis abu gelap dinamis (kontras tinggi)
                                Text(
                                    text = news.description,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), // Dinamis kontras
                                    fontSize = 13.sp,
                                    maxLines = 3
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}