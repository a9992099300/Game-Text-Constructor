package com.a9992099300.gameTextConstructor.data.books.services.book

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.data.books.models.ChapterDataModel
import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel
import com.a9992099300.gameTextConstructor.data.common.ktor.AUTH
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


        val jsonObject: JsonObject? = builderJson.decodeFromString(stringBody)

        if (jsonObject != null) {
            for (i in jsonObject) {
                val book = builderJson.decodeFromString<BookDataModel?>(i.value.toString())
                book?.let { books.add(it) }
            }
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

    override suspend fun addBook(userId: String, model: BookDataModel, token: String) =
        httpClient.addToken.patch {
            url {
                path("users/${userId}/userBooks/${model.bookId}.json")
                url.parameters.append(AUTH, token)
                setBody(
                    model
                )
            }
        }

    override suspend fun addChapter(
        userId: String,
        bookId: String,
        model: ChapterDataModel,
        token: String
    ) =
        httpClient.addToken.patch {
            url {
                path("users/${userId}/userChapters/books/${bookId}/chapters/${model.chapterId}.json")
                setBody(
                    model
                )
                url.parameters.append(AUTH, token)
            }
        }

//    override suspend fun addPage(
//        userId: String,
//        bookId: String,
//        chapterId: String,
//        sceneId: String,
//        model: PageDataModel
//    ) = httpClient.addToken.patch {
//        url {
//            path("users/${userId}/userPages/books/${bookId}/chapters/${chapterId}/scenes/${sceneId}/page/${model.pageId}.json")
//            url.parameters.append(AUTH, token)
//            setBody(
//                model
//            )
//        }
//    }

    override suspend fun editBook(userId: String, model: BookDataModel) =
        httpClient.addToken.patch {
            url {
                path("users/${userId}/userBooks/${model.bookId}.json")
                setBody(
                    model
                )
            }
        }

    override suspend fun deleteBook(userId: String, bookId: String, token: String): HttpResponse {
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userBooks/${bookId}.json")
                url.parameters.append(AUTH, token)
            }
        }
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userScenes/books/${bookId}/.json")
                url.parameters.append(AUTH, token)
            }
        }
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userChapters/books/${bookId}/.json")
                url.parameters.append(AUTH, token)
            }
        }
        return httpClient.addToken.delete {
            url {
                path("users/${userId}/userPages/books/${bookId}/.json")
                url.parameters.append(AUTH, token)
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
        val jsonObject: JsonObject? = builderJson.decodeFromString(stringBody)

        if (jsonObject != null) {
            for (i in jsonObject) {
                val book = builderJson.decodeFromString<ChapterDataModel?>(i.value.toString())
                book?.let { chapters.add(it) }
            }
        }

        return chapters.toList()
    }

    override suspend fun getChaptersId(userId: String, bookId: String): List<String> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userChapters/books/${bookId}/chapters.json")
            }
            url.parameters.append("shallow", "true")
        }
        val chapters: MutableList<String> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject? = builderJson.decodeFromString(stringBody)

        if (jsonObject != null) {
            for (i in jsonObject) {
                chapters.add(i.key)
            }
        }
        return chapters.toList()
    }

    override suspend fun getPages(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String
    ): List<PageDataModel> {

        val httpResponseAction: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userPages/books/${bookId}/chapters/${chapterId}/scenes/${sceneId}/page.json")
            }
        }
        val pages: MutableList<PageDataModel> = mutableListOf()
        val stringBody: String = httpResponseAction.body()
        val jsonObject: JsonObject? = builderJson.decodeFromString(stringBody)

        if (jsonObject != null) {
            for (i in jsonObject) {
                val page = builderJson.decodeFromString<PageDataModel?>(i.value.toString())
                page?.let { pages.add(it) }
            }
        }

        return pages.toList()
    }

    override suspend fun deleteChapter(userId: String, bookId: String, chapterId: String, token: String): HttpResponse {
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userScenes/books/${bookId}/chapters/${chapterId}.json")
                url.parameters.append(AUTH, token)
            }
        }
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userChapters/books/${bookId}/chapters/${chapterId}.json")
                url.parameters.append(AUTH, token)

            }
        }
        return httpClient.addToken.delete {
            url {
                path("users/${userId}/userPages/books/${bookId}/chapters/${chapterId}.json")
                url.parameters.append(AUTH, token)
            }
        }
    }
}