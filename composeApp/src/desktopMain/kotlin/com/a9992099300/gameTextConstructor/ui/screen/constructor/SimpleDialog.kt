package com.a9992099300.gameTextConstructor.ui.screen.constructor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.logic.constructor.listBooks.ListBookConstructorComponent

@Composable
private fun Dialog(
    component: ListBookConstructorComponent,
) {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        androidx.compose.ui.window.Dialog(
            onCloseRequest = {

            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(color = Color.White),
            ) {

            }
        }
    }
}