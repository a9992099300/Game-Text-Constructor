package com.a9992099300.gameTextConstructor.di.book

import com.a9992099300.gameTextConstructor.data.books.repository.BooksRepository
import com.a9992099300.gameTextConstructor.data.books.repository.BooksRepositoryImpl
import com.a9992099300.gameTextConstructor.data.books.services.BooksService
import com.a9992099300.gameTextConstructor.data.books.services.BooksServiceImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal val bookModule = DI.Module("bookModule") {
    bindProvider<BooksService>() { BooksServiceImpl(instance()) }
    bindProvider<BooksRepository>() { BooksRepositoryImpl(instance(), instance()) }
    }
