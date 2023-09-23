package com.a9992099300.gameTextConstructor.data.books.repository.chapter

import com.a9992099300.gameTextConstructor.data.books.models.ChapterDataModel
import com.a9992099300.gameTextConstructor.data.common.Result

interface ChaptersRepository {

    suspend fun addChapter(model: ChapterDataModel) : Result<ChapterDataModel>

    suspend fun getChapters(bookId: String) : Result<List<ChapterDataModel>>

    suspend fun getChapter(bookId: String, chapterId: String) : Result<ChapterDataModel>

    suspend fun getChaptersId(bookId: String): Result<List<String>>

    suspend fun deleteChapter(bookId: String, chapterId: String) : Result<Unit>

}