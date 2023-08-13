package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.theme.Theme


@Composable
fun CommonOutlineButton(
    onClickButton: () -> Unit,
    isLoading: Boolean = false,
    text: String,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        modifier = modifier
            .wrapContentWidth()
            .height(36.dp),
        onClick = onClickButton,
        colors = ButtonDefaults.buttonColors(
            contentColor = Theme.colors.primaryAction,
            containerColor = Theme.colors.secondaryBackground,
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        if (isLoading) {
                CircularProgressIndicator(
                    color = Theme.colors.secondaryTextColor
                )
        } else {
            Text(
                text,
                fontSize = 16.sp,
                style = MaterialTheme.typography.body1
            )
        }
    }
}