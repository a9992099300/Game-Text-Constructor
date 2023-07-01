package com.a9992099300.gameTextConstructor.logic.registration

import com.a9992099300.gameTextConstructor.logic.common.StateUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


interface RegistrationComponent {

    val stateUi: MutableStateFlow<StateUi<Unit>>

    val login: StateFlow<String>

    val password: StateFlow<String>

    fun onLoginChanged(login: String)

    fun onPasswordChanged(password: String)

    fun onRegistrationClick()

    fun onBack()

    sealed class Registration {
        object Back : Registration()
    }
}