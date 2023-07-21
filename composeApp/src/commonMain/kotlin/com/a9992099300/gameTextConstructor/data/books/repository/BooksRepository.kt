package com.a9992099300.gameTextConstructor.data.books.repository

import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.ui.screen.models.BookModel

interface BooksRepository {
    suspend fun getBooksList() : Result<List<BookDataModel>>

    suspend fun getBook(bookId: String) : Result<BookDataModel>

    suspend fun addBook(model: BookModel) : Result<BookDataModel>

    suspend fun editBook(model: BookDataModel) : Result<BookDataModel>

    suspend fun deleteBook(bookId: String) : Result<Unit>
}