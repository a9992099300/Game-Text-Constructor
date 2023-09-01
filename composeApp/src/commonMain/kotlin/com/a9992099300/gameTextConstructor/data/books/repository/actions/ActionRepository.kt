package com.a9992099300.gameTextConstructor.data.books.repository.actions

import com.a9992099300.gameTextConstructor.data.books.models.ItemPage
import com.a9992099300.gameTextConstructor.data.common.Result

interface ActionRepository {

    suspend fun getActions(
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String
    ): Result<List<ItemPage>>

    suspend fun getAction(
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String,
        actionId: String
    ): Result<ItemPage>

    suspend fun addAction(
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String,
        model: ItemPage
    ): Result<ItemPage>

    suspend fun getActionIds(
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String
    ): Result<List<String>>

    suspend fun deleteAction(
        bookId: String, chapterId: String, sceneId: String,
        pageId: String, actionId: String
    ): Result<Unit>
}