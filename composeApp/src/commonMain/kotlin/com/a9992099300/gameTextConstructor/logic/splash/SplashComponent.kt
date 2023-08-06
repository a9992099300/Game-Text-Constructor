package com.a9992099300.gameTextConstructor.logic.splash

import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import kotlinx.coroutines.flow.StateFlow

interface SplashComponent: BaseComponent<Unit> {

    val name: StateFlow<String>

    sealed class Splash {
        object Login : Splash()
        object Main : Splash()
    }
}