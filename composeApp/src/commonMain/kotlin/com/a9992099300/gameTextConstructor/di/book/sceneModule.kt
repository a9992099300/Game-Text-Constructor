package com.a9992099300.gameTextConstructor.di.book

import com.a9992099300.gameTextConstructor.data.books.repository.scenes.ScenesRepository
import com.a9992099300.gameTextConstructor.data.books.repository.scenes.ScenesRepositoryImpl
import com.a9992099300.gameTextConstructor.data.books.services.scene.ScenesService
import com.a9992099300.gameTextConstructor.data.books.services.scene.ScenesServiceImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal val sceneModule = DI.Module("sceneModule") {
    bindProvider<ScenesService>() { ScenesServiceImpl(instance()) }
    bindProvider<ScenesRepository>() { ScenesRepositoryImpl(instance(), instance()) }
    }
