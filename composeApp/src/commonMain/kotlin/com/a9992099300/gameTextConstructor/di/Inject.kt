package com.a9992099300.gameTextConstructor.di

import com.a9992099300.gameTextConstructor.data.common.ktor.ktorModule
import com.a9992099300.gameTextConstructor.di.login.authModule
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.instance
import org.kodein.di.singleton

object Inject {

    private var _di: DirectDI? = null

    val di: DirectDI
        get() = requireNotNull(_di)


    fun initDI(
        config: PlatformConfiguration
    ) {

        val platformModule = DI.Module(name = "platformModule",
            init = {
                bind<PlatformConfiguration>() with singleton { config }

            })

           _di = DI {
                importAll(
                    platformModule,
                    authModule,
                    ktorModule,
                )
            }.direct
    }

    inline fun<reified T> instance(): T{
        return di.instance()
    }
}