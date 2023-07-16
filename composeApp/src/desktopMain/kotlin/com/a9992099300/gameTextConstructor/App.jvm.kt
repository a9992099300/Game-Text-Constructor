package com.a9992099300.gameTextConstructor

import com.a9992099300.gameTextConstructor.di.PlatformConfiguration
import java.awt.Desktop
import java.net.URI

internal actual fun openUrl(url: String?) {
    val uri = url?.let { URI.create(it) } ?: return
    Desktop.getDesktop().browse(uri)
}

internal actual fun finish(platformConfiguration: PlatformConfiguration) {
    platformConfiguration.applicationScope.exitApplication()
}