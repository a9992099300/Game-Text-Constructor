package com.a9992099300.gameTextConstructor.data.books.repository

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.data.books.models.ChapterDataModel.Companion.createEmptyChapter
import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel.Companion.createEmptyPage
import com.a9992099300.gameTextConstructor.data.books.models.SceneDataModel.Companion.createEmptyScene
import com.a9992099300.gameTextConstructor.data.books.services.BooksService
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
            val chapter = createEmptyChapter(bookId = bookId)
            bookListBooksService.addChapter(
                userId = userIdOwner,
                bookId = bookId,
                model = chapter
            )
            val scene = createEmptyScene(chapterId = chapter.chapterId)
            bookListBooksService.addScene(
                userId = userIdOwner,
                bookId = bookId,
                chapterId = chapter.chapterId,
                model = scene
            )
            bookListBooksService.addPage(
                userId = userIdOwner,
                bookId = bookId,
                chapterId = chapter.chapterId,
                sceneId = scene.sceneId,
                model = createEmptyPage(sceneId = scene.sceneId)
            )
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
}


