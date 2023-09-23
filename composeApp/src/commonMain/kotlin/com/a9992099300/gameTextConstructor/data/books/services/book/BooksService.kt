package com.a9992099300.gameTextConstructor.data.books.services.book

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import io.ktor.client.statement.HttpResponse

interface BooksService{
     suspend fun getBooksList(userId: String) : List<BookDataModel>

     suspend fun getBooksListSize(userId: String): Int

     suspend fun getBook(userId: String, bookId: String) : HttpResponse

     suspend fun addBook(userId: String, model: BookDataModel
                         ,token: String
     ) : HttpResponse

     suspend fun editBook(userId: String, model: BookDataModel) : HttpResponse

     suspend fun deleteBook(userId: String, bookId: String, token: String): HttpResponse
}