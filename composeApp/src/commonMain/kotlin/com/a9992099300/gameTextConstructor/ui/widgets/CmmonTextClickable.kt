package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.theme.Theme

@Composable
fun CommonTextClickable(
    text: String,
    modifier: Modifier = Modifier,
    size: Int = 16,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit
) {
    Text(
        text = text,
        modifier = modifier
            .clickable {
            onClick()
        },
        color = Theme.colors.primaryAction,
        textAlign = textAlign,
        style = androidx.compose.material.MaterialTheme.typography.h2,
        fontSize = size.sp,
    )
}