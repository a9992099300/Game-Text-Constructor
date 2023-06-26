package com.a9992099300.gameTextConstructor.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.auth.SignInComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignScreen(component: SignInComponent) {

    val login by component.login.collectAsState()
    val password by component.password.collectAsState()
    val inProgress by component.inProgress.collectAsState()

    Column {
        TextField(
            value = login,
            onValueChange = component::onLoginChanged
        )

        TextField(
            value = password,
            onValueChange = component::onPasswordChanged,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        if (inProgress) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = component::onSignInClick) {
                    Text(MainRes.string.simple_string)
            }
        }
    }
}