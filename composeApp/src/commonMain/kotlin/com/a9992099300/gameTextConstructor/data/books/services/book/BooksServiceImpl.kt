package com.a9992099300.gameTextConstructor.data.books.services.book

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.data.books.models.ChapterDataModel
import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel
import com.a9992099300.gameTextConstructor.data.common.ktor.HttpClientWrapper
import com.a9992099300.gameTextConstructor.data.common.ktor.UID
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

    override suspend fun addBook(userId: String, model: BookDataModel) =
        httpClient.addToken.patch {
            url {
                path("users/${userId}/userBooks/${model.bookId}.json")
                setBody(
                    model
                )
                url.parameters.append("auth", "eyJhbGciOiJSUzI1NiIsImtpZCI6IjYzODBlZjEyZjk1ZjkxNmNhZDdhNGNlMzg4ZDJjMmMzYzIzMDJmZGUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vZ2FtZS10ZXh0LWNvbnN0cnVjdG9yIiwiYXVkIjoiZ2FtZS10ZXh0LWNvbnN0cnVjdG9yIiwiYXV0aF90aW1lIjoxNjkyMTk2Mjc2LCJ1c2VyX2lkIjoiYmVsSENvS2tyS1Bud2d6R044T040djU1eGowMiIsInN1YiI6ImJlbEhDb0trcktQbndnekdOOE9ONHY1NXhqMDIiLCJpYXQiOjE2OTIxOTYyNzYsImV4cCI6MTY5MjE5OTg3NiwiZW1haWwiOiJhOTk5MjA5OTMwMEB5YW5kZXgucnUiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsiYTk5OTIwOTkzMDBAeWFuZGV4LnJ1Il19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.lVbcZim0lPTU2-ggUgy-u9iqsYEBnABQuVEIuZFVJ4Vk-lPrYSY3sPtz6BXx8AWBWQRLJHEqhs3DFcqr8AygG7BnW_U0yvAPttrOux9qd5VQEDyp8O62vmz5WkpF5M8d0AtzLxbKGdlZROqDt0n_l1qyrp9FDBDl1b5e5mRK8uDAn1YRlezhbS4Z9stst9MVxrp0HhU944K8qjTgqEEIGutd21pAptjDE1N5IYHup1ZanZkFTkBA2uEjWVVGq5JW0sb09-h6L14YJnAq7Eg5DbpO2w0u1lTaunZF-1aIJ7xjv0u2xGlNPlVxdepa77PNWJr2XtvZP1giewjTiDxARg")
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

    override suspend fun addPage(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        model: PageDataModel
    ) = httpClient.addToken.patch {
        url {
            path("users/${userId}/userPages/books/${bookId}/chapters/${chapterId}/scenes/${sceneId}/page/${model.pageId}.json")
            setBody(
                model
            )
        }
    }

    override suspend fun editBook(userId: String, model: BookDataModel) =
        httpClient.addToken.patch {
            url {
                parameters.append(UID, userId)
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

//    override suspend fun getPages(
//        userId: String,
//        bookId: String,
//        chapterId: String,
//        sceneId: String
//    ): List<PageDataModel>

    override suspend fun deleteChapter(userId: String, bookId: String, chapterId: String): HttpResponse {
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userScenes/books/${bookId}/chapters/${chapterId}.json")
            }
        }
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userChapters/books/${bookId}/chapters/${chapterId}.json")
            }
        }
        return httpClient.addToken.delete {
            url {
                path("users/${userId}/userPages/books/${bookId}/chapters/${chapterId}.json")
            }
        }
    }
}