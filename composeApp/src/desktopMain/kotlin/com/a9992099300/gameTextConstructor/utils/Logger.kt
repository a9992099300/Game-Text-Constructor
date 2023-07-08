package com.a9992099300.gameTextConstructor.utils

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

internal actual fun initLogger() {
    Napier.base(DebugAntilog())
}