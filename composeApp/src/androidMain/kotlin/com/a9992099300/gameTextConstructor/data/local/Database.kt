package com.a9992099300.gameTextConstructor.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.a9992099300.gameTextConstructor.db.MyDatabase
import com.a9992099300.gameTextConstructor.di.PlatformConfiguration

actual class DriverFactory actual constructor(
   private val platform: PlatformConfiguration
) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MyDatabase.Schema, platform.context, "authDb.db")
    }
}
