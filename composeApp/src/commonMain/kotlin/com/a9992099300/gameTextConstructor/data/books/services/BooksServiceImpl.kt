package com.a9992099300.gameTextConstructor.data.books.services

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.data.books.models.ChapterDataModel
import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel
import com.a9992099300.gameTextConstructor.data.books.models.SceneDataModel
import com.a9992099300.gameTextConstructor.data.common.ktor.HttpClientWrapper
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

class BooksServiceImpl(
    private val httpClient: HttpClientWrapper
) : BooksService {

    private val builderJson = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    override suspend fun getBooksList(userId: String): List<BookDataModel> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userBooks.json")
            }
        }
        val books: MutableList<BookDataModel> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject = builderJson.decodeFromString(stringBody)

        for (i in jsonObject) {
            val book = Json.decodeFromString<BookDataModel>(i.value.toString())
            books.add(book)
        }
        return books.toList()
    }

    override suspend fun getBooksListSize(userId: String): Int {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userBooks.json")
            }
            url.parameters.append("shallow", "true")
        }
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject = Json.decodeFromString(stringBody)
        return jsonObject.size
    }

    override suspend fun getBook(userId: String, bookId: String) =
        httpClient.addToken.get {
            url {
                path("users/${userId}/userBooks/${bookId}/.json")
            }
        }

    override suspend fun addBook(userId: String, model: BookDataModel) =
        httpClient.addToken.patch {
            url {
                path("users/${userId}/userBooks/${model.bookId}.json")
                setBody(
                    model
                )
            }
        }

    override suspend fun addChapter(userId: String, bookId: String, model: ChapterDataModel) =
        httpClient.addToken.patch {
            url {
                path("users/${userId}/userChapters/books/${bookId}/chapters/${model.chapterId}.json")
                setBody(
                    model
                )
            }
        }

    override suspend fun addScene(
        userId: String,
        bookId: String,
        chapterId: String,
        model: SceneDataModel
    ) = httpClient.addToken.patch {
        url {
            path("users/${userId}/userScenes/books/${bookId}/chapter/${chapterId}/scenes/${model.sceneId}.json")
            setBody(
                model
            )
        }
    }

    override suspend fun addPage(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        model: PageDataModel
    ) = httpClient.addToken.patch {
        url {
            path("users/${userId}/userPages/books/${bookId}/chapter/${chapterId}/scenes/${sceneId}/page/${model.pageId}.json")
            setBody(
                model
            )
        }
    }

    override suspend fun editBook(userId: String, model: BookDataModel) =
        httpClient.addToken.patch {
            url {
                path("users/${userId}/userBooks/${model.bookId}.json")
                setBody(
                    model
                )
            }
        }

    override suspend fun deleteBook(userId: String, bookId: String): HttpResponse {
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userBooks/${bookId}.json")
            }
        }
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userScenes/books/${bookId}/.json")
            }
        }
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userChapters/books/${bookId}/.json")
            }
        }
        return httpClient.addToken.delete {
            url {
                path("users/${userId}/userPages/books/${bookId}/.json")
            }
        }
    }

    override suspend fun getChapters(userId: String, bookId: String): List<ChapterDataModel> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userChapters/books/${bookId}/chapters.json")
            }
        }
        val chapters: MutableList<ChapterDataModel> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject = builderJson.decodeFromString(stringBody)

        for (i in jsonObject) {
            val chapter = Json.decodeFromString<ChapterDataModel>(i.value.toString())
            chapters.add(chapter)
        }
        return chapters.toList()
    }

    override suspend fun getScenes(
        userId: String,
        bookId: String,
        chapterId: String
    ): List<SceneDataModel> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userScenes/books/${bookId}/chapter/${chapterId}/scenes.json")
            }
        }
        val scenes: MutableList<SceneDataModel> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject = builderJson.decodeFromString(stringBody)

        for (i in jsonObject) {
            val scene = Json.decodeFromString<SceneDataModel>(i.value.toString())
            scenes.add(scene)
        }
        return scenes.toList()
    }

    override suspend fun getPages(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String
    ): List<PageDataModel> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userPages/books/${bookId}/chapter/${chapterId}/scenes/${sceneId}/page.json")
            }
        }
        val scenes: MutableList<PageDataModel> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject = builderJson.decodeFromString(stringBody)

        for (i in jsonObject) {
            val scene = Json.decodeFromString<PageDataModel>(i.value.toString())
            scenes.add(scene)
        }
        return scenes.toList()
    }
}