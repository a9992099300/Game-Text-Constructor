package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.MainRes


@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun CommonDialog(
    title: String,
    text: String,
    onSuccess: () -> Unit,
    onCanceled: () -> Unit,
    ) {

    AlertDialog(
        onDismissRequest = onCanceled,
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = onCanceled) {
                    Text(text = MainRes.string.cancel)
                }

                TextButton(onClick = onSuccess) {
                    Text(text = MainRes.string.yes)
                }
            }
        },
        title = { CommonText(text = text, modifier = Modifier.fillMaxWidth()) },
        text = { CommonText(text = title, modifier = Modifier.fillMaxWidth()) },
        modifier = Modifier.width(400.dp),
    )

}
