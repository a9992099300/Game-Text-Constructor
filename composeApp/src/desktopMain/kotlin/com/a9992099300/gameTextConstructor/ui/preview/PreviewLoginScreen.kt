package com.a9992099300.gameTextConstructor.ui.preview

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.login.LogInComponent
import com.a9992099300.gameTextConstructor.ui.screen.LoginScreen
import kotlinx.coroutines.flow.MutableStateFlow


@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(LoginComponentImpl())
}

class LoginComponentImpl : LogInComponent {
    override val login = MutableStateFlow("")

    override val password = MutableStateFlow(Pair("", true))

    override fun onLoginChanged(login: String) { }

    override fun onPasswordChanged(password: String) {}

    override fun onVisibleChanged(visible: Boolean) { }

    override fun onSignInClick() { }

    override fun onRegistrationClick() { }

    override val onBack: () -> Unit = {

    }
    override val stateUi = MutableStateFlow(StateUi.Initial)

}