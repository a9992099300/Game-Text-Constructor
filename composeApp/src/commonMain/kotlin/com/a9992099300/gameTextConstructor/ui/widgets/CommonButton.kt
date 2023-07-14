package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.theme.Theme


@Composable
fun CommonButton(
    onClickButton: () -> Unit,
    isLoading: Boolean,
    text: String
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = onClickButton,
        colors = ButtonDefaults.buttonColors(
            contentColor = Theme.colors.secondaryTextColor,
            containerColor = Theme.colors.primaryAction,
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                text,
                fontSize = 18.sp
            )
        }
    }
}