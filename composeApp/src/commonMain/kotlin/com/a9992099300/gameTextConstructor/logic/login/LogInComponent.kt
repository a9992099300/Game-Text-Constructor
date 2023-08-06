package com.a9992099300.gameTextConstructor.logic.login

import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import kotlinx.coroutines.flow.StateFlow

interface LogInComponent: BaseComponent<Unit> {

    val login: StateFlow<String>

    val password: StateFlow<Pair<String,Boolean>>

    fun onLoginChanged(login: String)

    fun onPasswordChanged(password: String)

    fun onVisibleChanged(visible: Boolean)

    fun onSignInClick()

    fun onRegistrationClick()

    sealed class Login {
        object Main : Login()
        object RootConstructor : Login()
        object Registration : Login()

    }
}