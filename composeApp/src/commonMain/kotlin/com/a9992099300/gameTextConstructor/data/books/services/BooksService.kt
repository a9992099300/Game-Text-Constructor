package com.a9992099300.gameTextConstructor.data.books.services

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel

interface BooksService{
     suspend fun getBooksList(userId: String) : List<BookDataModel>

     suspend fun addBook(userId: String, model: BookDataModel) : BookDataModel
}