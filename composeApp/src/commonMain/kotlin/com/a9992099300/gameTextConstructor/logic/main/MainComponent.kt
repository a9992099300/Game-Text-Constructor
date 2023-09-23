package com.a9992099300.gameTextConstructor.logic.main

import com.a9992099300.gameTextConstructor.logic.base.BaseComponent


interface MainComponent: BaseComponent<Unit> {

    sealed class Main {
        object Exit : Main()
    }
}