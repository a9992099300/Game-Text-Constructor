package com.a9992099300.gameTextConstructor.di

import androidx.compose.ui.window.ApplicationScope

actual class PlatformConfiguration constructor(
    val applicationScope: ApplicationScope
) {
    actual fun getName(): Platform = Platform.DESKTOP
}