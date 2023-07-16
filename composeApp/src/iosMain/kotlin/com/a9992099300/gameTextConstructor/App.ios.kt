package com.a9992099300.gameTextConstructor

import com.a9992099300.gameTextConstructor.di.PlatformConfiguration
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

internal actual fun openUrl(url: String?) {
    val nsUrl = url?.let { NSURL.URLWithString(it) } ?: return
    UIApplication.sharedApplication.openURL(nsUrl)
}

internal actual fun finish(platformConfiguration: PlatformConfiguration) {

}