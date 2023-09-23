package com.a9992099300.gameTextConstructor.di.book

import com.a9992099300.gameTextConstructor.data.books.repository.chapter.ChapterRepositoryImpl
import com.a9992099300.gameTextConstructor.data.books.repository.chapter.ChaptersRepository
import com.a9992099300.gameTextConstructor.data.books.services.chapter.ChapterService
import com.a9992099300.gameTextConstructor.data.books.services.chapter.ChapterServiceImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal val chapterModule = DI.Module("chapterModule") {
    bindProvider<ChapterService>() { ChapterServiceImpl(instance()) }
    bindProvider<ChaptersRepository>() { ChapterRepositoryImpl(instance(), instance()) }
    }
