package com.a9992099300.gameTextConstructor.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.a9992099300.gameTextConstructor.db.MyDatabase
import com.a9992099300.gameTextConstructor.di.PlatformConfiguration


actual class DriverFactory actual constructor(
    private val platform: PlatformConfiguration
) {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        MyDatabase.Schema.create(driver)
        return driver
    }
}