package com.a9992099300.gameTextConstructor.di

import com.a9992099300.gameTextConstructor.data.local.DriverFactory
import com.a9992099300.gameTextConstructor.db.MyDatabase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

internal val databaseModule = DI.Module("databaseModule") {
    bind<MyDatabase>() with singleton {
        val driver = DriverFactory(instance()).createDriver()
         MyDatabase(driver)
    }
}
