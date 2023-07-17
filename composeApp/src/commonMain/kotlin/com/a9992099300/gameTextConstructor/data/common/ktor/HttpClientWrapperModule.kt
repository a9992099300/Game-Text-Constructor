package com.a9992099300.gameTextConstructor.data.common.ktor

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val httpClientWrapperModule =  DI.Module("httpClientWrapperModule")  {
    bind<HttpClientWrapper>()  with singleton {
        HttpClientWrapperImpl(instance(), instance(tag = "database"), instance()) }
}