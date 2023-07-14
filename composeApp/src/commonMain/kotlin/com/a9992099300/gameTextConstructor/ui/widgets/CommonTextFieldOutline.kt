package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextFieldOutline(
    text: String,
    hint: String,
    enabled: Boolean = true,
    isSecure: Boolean = false,
    trailingIcon: @Composable () -> Unit = {},
    onValueChanged: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        value = text,
        onValueChange = {
            onValueChanged.invoke(it)
        },
        label = { Text(hint) },
        enabled = enabled,
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
        colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
            textColor = Theme.colors.primaryTextColor,
            cursorColor = Theme.colors.primaryTextColor,
            focusedBorderColor = Theme.colors.primaryBackground
        )
    )
}