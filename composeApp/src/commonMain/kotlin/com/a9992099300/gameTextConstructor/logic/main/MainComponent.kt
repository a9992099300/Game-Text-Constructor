package com.a9992099300.gameTextConstructor.logic.main


interface MainComponent {

    sealed class Main {
        object Exit : Main()
    }
}