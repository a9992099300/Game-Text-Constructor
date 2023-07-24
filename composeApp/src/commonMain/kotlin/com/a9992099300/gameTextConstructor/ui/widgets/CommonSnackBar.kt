package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.data.auth.services.log
import com.a9992099300.gameTextConstructor.theme.Theme
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

@Composable
fun CommonSnackBar(
    message: String,
    imageVector: ImageVector = Icons.Default.Warning,
    dismissSnack: (() -> Unit)? = null
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Napier.d("CommonSnackBar", tag = log)
    scope.launch {
        snackbarHostState.
        showSnackbar("Error : $message", duration = SnackbarDuration.Indefinite)
    }

    SnackbarHost(
        modifier = Modifier
            .padding(0.dp, 25.dp, 0.dp, 0.dp)
            .fillMaxWidth(),
        hostState = snackbarHostState,
        snackbar = { snackbarData: SnackbarData ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Card(
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, Theme.colors.primaryAction),
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentWidth()
                        .widthIn(0.dp, 600.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                            .align(Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        Icon(imageVector = imageVector, contentDescription = "")


                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "${MainRes.string.error}: $message")


                        Button(
                            onClick = {
                                dismissSnack?.let {
                                    it()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Theme.colors.secondaryTextColor,
                                containerColor = Theme.colors.primaryAction,
                            ),
                        ) {
                            Text(MainRes.string.close)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    )
}