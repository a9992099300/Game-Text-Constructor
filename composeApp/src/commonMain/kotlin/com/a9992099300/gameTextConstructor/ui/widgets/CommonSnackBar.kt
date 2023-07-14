package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun CommonSnackBar(
    message: String,
    imageVector: ImageVector = Icons.Default.Warning,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    scope.launch {
        snackbarHostState.showSnackbar("Error : $message")
    }

    SnackbarHost(
        modifier = Modifier
            .padding(0.dp, 25.dp, 0.dp, 0.dp)
            .fillMaxWidth(),
        hostState = snackbarHostState,
        snackbar = { snackbarData: SnackbarData ->
            Card(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.White),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = imageVector, contentDescription = "")
                    Text(text = "Error : $message")
                }
            }
        }
    )
}