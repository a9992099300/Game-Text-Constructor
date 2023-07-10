package com.a9992099300.gameTextConstructor.logic.constructor.child

interface ProfileConstructorComponent {

    sealed class Main {
        object Exit : Main()
    }

    sealed class Child
}