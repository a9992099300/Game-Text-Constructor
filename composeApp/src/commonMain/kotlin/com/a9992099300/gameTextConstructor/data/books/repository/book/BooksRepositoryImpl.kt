package com.a9992099300.gameTextConstructor.data.books.repository.book

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.data.books.models.ChapterDataModel
import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel
import com.a9992099300.gameTextConstructor.data.books.services.book.BooksService
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import com.a9992099300.gameTextConstructor.data.common.allowRequest
import com.a9992099300.gameTextConstructor.data.common.request
import com.a9992099300.gameTextConstructor.data.common.simpleRequest
import com.a9992099300.gameTextConstructor.ui.screen.models.BookModel
import io.github.xxfast.kstore.KStore
import kotlinx.datetime.Clock

class BooksRepositoryImpl(
    private val bookListBooksService: BooksService,
    private val store: KStore<SavedAuth>
) : BooksRepository {
    override suspend fun getBooksList(): Result<List<BookDataModel>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest {
            bookListBooksService.getBooksList(
                userId = it
            )
        }
    }

    override suspend fun getBook(bookId: String): Result<BookDataModel> = request {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest {
            bookListBooksService.getBook(
                userId = it,
                bookId = bookId
            )
        }
    }

    override suspend fun addBook(model: BookModel): Result<BookDataModel> = request {
        val userId = store.get()?.firstOrNull()?.localId

        val date = Clock.System.now().toEpochMilliseconds()
        val booksListSize = userId?.let { getBooksSize(it) } ?: "0"
        userId?.let { userIdOwner ->
            val bookId = "${userIdOwner}_${date}_$booksListSize"
            bookListBooksService.addBook(
                userId = userIdOwner,
                BookDataModel(
                    bookId = bookId,
                    userIdOwner = userIdOwner,
                    title = model.title,
                    description = model.description,
                    category = model.category,
                    imageUrl = "",
                    createdDate = date.toString(),
                    deletable = true
                )
            )
        }
    }

    override suspend fun addChapter(model: ChapterDataModel) : Result<ChapterDataModel> = request {
        val userId = store.get()?.firstOrNull()?.localId
        userId?.let {
            bookListBooksService.addChapter(it, model.bookId, model)
        }
    }

    private suspend fun getBooksSize(userId: String): String {
        val result = simpleRequest {
            bookListBooksService.getBooksListSize(userId)
        }
        return when (result) {
            is Result.Success -> result.value.toString()
            is Result.Error -> "0"
        }
    }

    override suspend fun editBook(model: BookDataModel): Result<BookDataModel> = request {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest { userIdOwner ->
            bookListBooksService.addBook(
                userId = userIdOwner,
                model = model
            )
        }
    }

    override suspend fun deleteBook(bookId: String): Result<Unit> = request {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest { userIdOwner ->
            bookListBooksService.deleteBook(
                userId = userIdOwner,
                bookId = bookId
            )
        }
    }

    override suspend fun getChapters(bookId: String): Result<List<ChapterDataModel>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId?.let { bookListBooksService.getChapters(it, bookId) }
    }

    override suspend fun getPages(
        bookId: String,
        chapterId: String,
        sceneId: String
    ): Result<List<PageDataModel>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId?.let { bookListBooksService.getPages(it,  bookId, chapterId, sceneId) }
    }

    override suspend fun getChaptersId(bookId: String): Result<List<String>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest{
            bookListBooksService.getChaptersId(it, bookId)
        }
    }

    override suspend fun deleteChapter(bookId: String, chapterId: String): Result<Unit> = request{
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest{
            bookListBooksService.deleteChapter(it, bookId, chapterId)
        }
    }
}


