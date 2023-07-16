package com.a9992099300.gameTextConstructor

import com.a9992099300.gameTextConstructor.di.PlatformConfiguration
import kotlinx.browser.window

internal actual fun openUrl(url: String?) {
    url?.let { window.open(it) }
}

internal actual fun finish(platformConfiguration: PlatformConfiguration) {

}