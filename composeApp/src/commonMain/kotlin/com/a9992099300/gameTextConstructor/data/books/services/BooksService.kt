package com.a9992099300.gameTextConstructor.data.books.services

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.data.books.models.ChapterDataModel
import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel
import com.a9992099300.gameTextConstructor.data.books.models.SceneDataModel
import io.ktor.client.statement.HttpResponse

interface BooksService{
     suspend fun getBooksList(userId: String) : List<BookDataModel>

     suspend fun getBooksListSize(userId: String): Int

     suspend fun getBook(userId: String, bookId: String) : HttpResponse

     suspend fun addBook(userId: String, model: BookDataModel) : HttpResponse

     suspend fun addChapter(userId: String, bookId: String, model: ChapterDataModel) : HttpResponse
     suspend fun addScene(
          userId: String,
          bookId: String,
          chapterId: String,
          model: SceneDataModel
     ) : HttpResponse

     suspend fun addPage(
          userId: String,
          bookId: String,
          chapterId: String,
          sceneId: String,
          model: PageDataModel
     ) : HttpResponse

     suspend fun editBook(userId: String, model: BookDataModel) : HttpResponse

     suspend fun deleteBook(userId: String, bookId: String): HttpResponse

     suspend fun getChapters(userId: String, bookId: String): List<ChapterDataModel>

     suspend fun getScenes(userId: String, bookId: String, chapterId: String) : List<SceneDataModel>

     suspend fun getPages(userId: String, bookId: String, chapterId: String, sceneId: String) : List<PageDataModel>

     suspend fun getChaptersId(userId: String, bookId: String): List<String>
}