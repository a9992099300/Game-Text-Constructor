package com.a9992099300.gameTextConstructor.di.profile

import com.a9992099300.gameTextConstructor.data.profile.repository.UserRepository
import com.a9992099300.gameTextConstructor.data.profile.repository.UserRepositoryImpl
import com.a9992099300.gameTextConstructor.data.profile.services.UserService
import com.a9992099300.gameTextConstructor.data.profile.services.UserServiceImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal val profileModule = DI.Module("profileModule") {
        bindProvider<UserService>() { UserServiceImpl(instance()) }
        bindProvider<UserRepository>() { UserRepositoryImpl(instance(), instance()) }
    }
