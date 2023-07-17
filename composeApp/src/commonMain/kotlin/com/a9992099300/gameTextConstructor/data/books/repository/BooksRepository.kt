package com.a9992099300.gameTextConstructor.data.books.repository

import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel

interface BooksRepository {
    suspend fun getBooksList() : Result<List<BookDataModel>>

    suspend fun addBook(model: BookDataModel) : Result<BookDataModel>
}