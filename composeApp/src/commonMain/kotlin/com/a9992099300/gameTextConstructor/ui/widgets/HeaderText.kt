package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.theme.Theme

@Composable
fun HeaderText(
    text: String
) {
    Text(
        text = text,
        modifier = Modifier.wrapContentWidth(),
        color = Theme.colors.primaryAction,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 24.sp
    )
}