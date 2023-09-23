package com.a9992099300.gameTextConstructor.di

expect class PlatformConfiguration {
    fun getName(): Platform
}

enum class Platform{
    ANDROID, IOS, DESKTOP
}
