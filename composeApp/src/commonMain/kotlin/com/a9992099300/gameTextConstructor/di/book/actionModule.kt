package com.a9992099300.gameTextConstructor.di.book

import com.a9992099300.gameTextConstructor.data.books.repository.actions.ActionRepository
import com.a9992099300.gameTextConstructor.data.books.repository.actions.ActionRepositoryImpl
import com.a9992099300.gameTextConstructor.data.books.services.actions.ActionService
import com.a9992099300.gameTextConstructor.data.books.services.actions.ActionServiceImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal val actionModule = DI.Module("actionModule") {
    bindProvider<ActionService>() { ActionServiceImpl(instance()) }
    bindProvider<ActionRepository>() { ActionRepositoryImpl(instance(), instance()) }
}
