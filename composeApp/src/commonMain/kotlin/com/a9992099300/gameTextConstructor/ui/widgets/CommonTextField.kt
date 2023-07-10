package com.a9992099300.gameTextConstructor.ui.widgets


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.theme.Theme

@Composable
fun CommonTextField(
    text: String,
    hint: String,
    enabled: Boolean = true,
    isSecure: Boolean = false,
    trailingIcon: @Composable () -> Unit = {},
    onValueChanged: (String) -> Unit,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        value = text,
        enabled = enabled,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Theme.colors.textFieldBackground,
            textColor = Theme.colors.primaryTextColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Theme.colors.primaryTextColor
        ),
      //  textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        placeholder = {
            Text(
                text = hint,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp,0.dp,0.dp,0.dp),
                color = Theme.colors.primaryBackground,
                textAlign = TextAlign.Start,
                style = typography.bodyMedium,
                fontSize = 16.sp
            )
        },
        visualTransformation =
        if (isSecure) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {
                       trailingIcon.invoke()
        },
        onValueChange = {
           onValueChanged.invoke(it)
        })
}