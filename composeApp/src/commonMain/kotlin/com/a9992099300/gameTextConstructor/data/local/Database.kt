package com.a9992099300.gameTextConstructor.data.local

import app.cash.sqldelight.db.SqlDriver
import com.a9992099300.gameTextConstructor.di.PlatformConfiguration


expect class DriverFactory(
    platform: PlatformConfiguration
) {
    fun createDriver(): SqlDriver
}
