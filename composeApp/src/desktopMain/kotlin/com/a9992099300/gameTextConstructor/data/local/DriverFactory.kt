package com.a9992099300.gameTextConstructor.data.local

import com.a9992099300.gameTextConstructor.utils.ApplicationName
import net.harawata.appdirs.AppDirsFactory


//actual class DriverFactory actual constructor(platform: PlatformConfiguration) {
//    actual fun createDriver(): SqlDriver {
//        val databasePath = File(System.getProperty("java.io.tmpdir"), "authBD2.db")
//        val driver: SqlDriver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}")
//        return driver.apply {
//            try {
//                MyDatabase.Schema.create(this)
//            }catch (e: Exception){
//
//            }
//        }
//    }
//    }

    private fun pathDatabase(id: String): String {
        val appDir = AppDirsFactory.getInstance().getUserDataDir(
            ApplicationName,
            "1.0.0",
            "com.a9992099300"
        )
        return "$appDir/$id.db"
    }



