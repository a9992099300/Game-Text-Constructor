package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.runtime.Composable

@Composable
expect fun CommonDialog(
    text: String,
    onSuccess: () -> Unit,
    onCanceled: () -> Unit,
)