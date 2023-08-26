package com.a9992099300.gameTextConstructor.data.books.repository.pages

import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel
import com.a9992099300.gameTextConstructor.data.common.Result

interface PagesRepository {

    suspend fun getPages(
        bookId: String,
        chapterId: String,
        sceneId: String
    ): Result<List<PageDataModel>>

    suspend fun getPage(
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String
    ): Result<PageDataModel>

    suspend fun addPage(model: PageDataModel): Result<PageDataModel>

    suspend fun getPagesIds(
        bookId: String,
        chapterId: String,
        sceneId: String,
    ): Result<List<String>>

    suspend fun deletePage(
        bookId: String, chapterId: String, sceneId: String,
        pageId: String
    ): Result<Unit>
}