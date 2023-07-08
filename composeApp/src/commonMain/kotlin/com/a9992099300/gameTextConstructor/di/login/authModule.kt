package com.a9992099300.gameTextConstructor.di.login

import com.a9992099300.gameTextConstructor.data.auth.repository.AuthRepository
import com.a9992099300.gameTextConstructor.data.auth.repository.AuthRepositoryImpl
import com.a9992099300.gameTextConstructor.data.auth.services.AuthService
import com.a9992099300.gameTextConstructor.data.auth.services.AuthServiceImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal val authModule = DI.Module("authModule") {
        bindProvider<AuthService>() { AuthServiceImpl(instance(), instance()) }
        bindProvider<AuthRepository>() { AuthRepositoryImpl(instance()) }
    }
