package com.a9992099300.gameTextConstructor.data.books.services.actions

import com.a9992099300.gameTextConstructor.data.books.models.ItemPage
import io.ktor.client.statement.HttpResponse

interface ActionService {

    suspend fun addAction(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String,
        model: ItemPage,
        token: String
    ): HttpResponse

    suspend fun getActions(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String
    ): List<ItemPage>

    suspend fun getAction(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String,
        actionId: String
    ): HttpResponse

    suspend fun getActionIds(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String,
    ): List<String>

    suspend fun deleteAction(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String,
        actionId: String,
        token: String
    ): HttpResponse
}