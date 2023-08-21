package com.a9992099300.gameTextConstructor.data.books.repository.book

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.data.books.models.ChapterDataModel
import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.ui.screen.models.BookModel

interface BooksRepository {
    suspend fun getBooksList() : Result<List<BookDataModel>>

    suspend fun getBook(bookId: String) : Result<BookDataModel>

    suspend fun addBook(model: BookModel) : Result<BookDataModel>

    suspend fun addChapter(model: ChapterDataModel) : Result<ChapterDataModel>

    suspend fun editBook(model: BookDataModel) : Result<BookDataModel>

    suspend fun deleteBook(bookId: String) : Result<Unit>

    suspend fun getChapters(bookId: String) : Result<List<ChapterDataModel>>

    suspend fun getPages(
        bookId: String,
        chapterId: String,
        sceneId: String
    ): Result<List<PageDataModel>>

    suspend fun getChaptersId(bookId: String): Result<List<String>>

    suspend fun deleteChapter(bookId: String, chapterId: String) : Result<Unit>

}