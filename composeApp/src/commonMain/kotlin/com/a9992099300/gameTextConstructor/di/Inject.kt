package com.a9992099300.gameTextConstructor.di

import com.a9992099300.gameTextConstructor.data.common.ktor.ktorModule
import com.a9992099300.gameTextConstructor.di.login.authModule
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.direct
import org.kodein.di.instance

object Inject {

    private var _di: DirectDI? = null

    private val modules = listOf<DI.Module>(authModule, ktorModule)

    val di: DirectDI
        get() = requireNotNull(_di)


    fun initDI() {
           _di = DI {
                importAll(
                    modules
                )
            }.direct
    }

    inline fun<reified T> instance(): T{
        return di.instance()
    }
}