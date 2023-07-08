package com.a9992099300.gameTextConstructor.logic.login

import com.a9992099300.gameTextConstructor.logic.common.StateUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface LogInComponent {

    val stateUi: MutableStateFlow<StateUi<Unit>>

    val login: StateFlow<String>

    val password: StateFlow<String>

    fun onLoginChanged(login: String)

    fun onPasswordChanged(password: String)

    fun onSignInClick()

    fun onRegistrationClick()

    sealed class Login {
        object Finished : Login()
        object Registration : Login()
    }
}