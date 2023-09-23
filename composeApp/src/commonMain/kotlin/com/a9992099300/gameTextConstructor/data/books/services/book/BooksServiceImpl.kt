package com.a9992099300.gameTextConstructor.data.books.services.book

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
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

}