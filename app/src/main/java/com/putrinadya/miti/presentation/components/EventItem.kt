package com.putrinadya.miti.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.putrinadya.miti.domain.model.Event
import com.putrinadya.miti.R
import com.putrinadya.miti.ui.theme.*

@Composable
fun EventItem(
    event: Event,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryColor = try {
        Color(android.graphics.Color.parseColor(event.categoryColorHex))
    } catch (e: Exception) {
        MaterialTheme.colorScheme.onBackground
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(48.dp)
                    .background(categoryColor, RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (event.title.isEmpty()) stringResource(R.string.no_title) else event.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (event.category.isEmpty()) stringResource(R.string.general_category) else event.category,
                        fontSize = 11.sp,
                        color = categoryColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(" • ", fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground)
                    Text(
                        text = "${event.dayMonth} ${event.year}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}