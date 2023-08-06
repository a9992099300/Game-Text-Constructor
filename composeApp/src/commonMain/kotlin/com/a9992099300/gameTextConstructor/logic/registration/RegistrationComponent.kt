package com.a9992099300.gameTextConstructor.logic.registration

import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import kotlinx.coroutines.flow.StateFlow


interface RegistrationComponent: BaseComponent<Unit> {

    val login: StateFlow<String>

    val password: StateFlow<String>

    fun onLoginChanged(login: String)

    fun onPasswordChanged(password: String)

    fun onRegistrationClick()

    sealed class Registration {
        object Back : Registration()
    }
}