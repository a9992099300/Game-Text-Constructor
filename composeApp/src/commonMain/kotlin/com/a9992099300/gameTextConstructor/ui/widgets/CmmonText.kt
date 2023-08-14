package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.theme.Theme

@Composable
fun CommonText(
    text: String,
    modifier: Modifier = Modifier,
    size: Int = 16,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Theme.colors.primaryAction,
        textAlign = textAlign,
        style = androidx.compose.material.MaterialTheme.typography.h2,
        fontSize = size.sp,
    )
}