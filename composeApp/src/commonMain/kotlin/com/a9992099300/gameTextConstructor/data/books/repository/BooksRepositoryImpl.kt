package com.a9992099300.gameTextConstructor.data.books.repository

import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import com.a9992099300.gameTextConstructor.data.common.request
import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.data.books.services.BooksService
import com.a9992099300.gameTextConstructor.data.common.allowRequest
import io.github.xxfast.kstore.KStore

class BooksRepositoryImpl(
    private val bookListBooksService: BooksService,
    private val store: KStore<SavedAuth>
) : BooksRepository {
    override suspend fun getBooksList() : Result<List<BookDataModel>>  = request {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest {
            bookListBooksService.getBooksList(
                userId = it
            )
        }
    }

    override suspend fun addBook(model: BookDataModel): Result<BookDataModel> = request {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest {
            bookListBooksService.addBook(
                userId = it,
                model
            )
        }
    }
}


