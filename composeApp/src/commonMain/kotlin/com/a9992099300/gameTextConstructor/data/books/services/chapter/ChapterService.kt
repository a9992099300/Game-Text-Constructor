package com.a9992099300.gameTextConstructor.data.books.services.chapter

import com.a9992099300.gameTextConstructor.data.books.models.ChapterDataModel
import io.ktor.client.statement.HttpResponse

interface ChapterService {

    suspend fun addChapter(
        userId: String,
        bookId: String,
        model: ChapterDataModel,
        token: String
    ): HttpResponse

    suspend fun getChapters(userId: String, bookId: String): List<ChapterDataModel>

    suspend fun getChapter(userId: String, bookId: String, chapterId: String): HttpResponse

    suspend fun getChaptersId(userId: String, bookId: String): List<String>

    suspend fun deleteChapter(
        userId: String,
        bookId: String,
        chapterId: String,
        token: String
    ): HttpResponse
}