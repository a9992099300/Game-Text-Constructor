package com.a9992099300.gameTextConstructor.logic.auth

import kotlinx.coroutines.flow.StateFlow

interface SignInComponent {

    val login: StateFlow<String>

    val password: StateFlow<String>

    val inProgress: StateFlow<Boolean>

    fun onLoginChanged(login: String)

    fun onPasswordChanged(password: String)

    fun onSignInClick()

    sealed class Login {
        object Finished : Login()
    }
}