package com.a9992099300.gameTextConstructor.di.book

import com.a9992099300.gameTextConstructor.data.books.repository.pages.PageRepositoryImpl
import com.a9992099300.gameTextConstructor.data.books.repository.pages.PagesRepository
import com.a9992099300.gameTextConstructor.data.books.services.page.PageService
import com.a9992099300.gameTextConstructor.data.books.services.page.PagesServiceImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal val pageModule = DI.Module("pageModule") {
    bindProvider<PageService>() { PagesServiceImpl(instance()) }
    bindProvider<PagesRepository>() { PageRepositoryImpl(instance(), instance()) }
}
