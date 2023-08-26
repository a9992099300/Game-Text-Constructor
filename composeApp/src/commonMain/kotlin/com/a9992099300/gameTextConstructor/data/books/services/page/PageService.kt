package com.a9992099300.gameTextConstructor.data.books.services.page

import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel
import io.ktor.client.statement.HttpResponse

interface PageService {

    suspend fun addPage(
        userId: String,
        model: PageDataModel,
        token: String
    ): HttpResponse

    suspend fun getPages(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String
    ): List<PageDataModel>

    suspend fun getPage(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String
    ): HttpResponse

    suspend fun getPageIds(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String
    ): List<String>

    suspend fun deletePage(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String,
        token: String
    ): HttpResponse
}