package com.a9992099300.gameTextConstructor.ui.screen.constructor.book

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog



@Composable
fun DialogChoosePage(modifier: Modifier) {
    Dialog(onDismissRequest = {  }) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.surface
        ) {



        }
    }
}