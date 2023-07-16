package com.a9992099300.gameTextConstructor.logic.splash

import com.a9992099300.gameTextConstructor.logic.common.StateUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface SplashComponent {

    val stateUi: MutableStateFlow<StateUi<Unit>>

    val name: StateFlow<String>

    sealed class Splash {
        object Login : Splash()
        object Main : Splash()
    }
}