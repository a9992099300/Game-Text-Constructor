package com.a9992099300.gameTextConstructor.data.books.services

import com.a9992099300.gameTextConstructor.data.common.ktor.HttpClientWrapper
import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
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
    override suspend fun getBooksList(userId: String): List<BookDataModel> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/books.json")
            }
        }
        val books: MutableList<BookDataModel> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject = Json.decodeFromString(stringBody)
        for (i in jsonObject) {
            val book = Json.decodeFromString<BookDataModel>(i.value.toString())
            books.add(book)
        }

        return books.toList()
    }

    override suspend fun getBook(userId: String, bookId: String) =
         httpClient.addToken.get {
        url {
            path("users/${userId}/books/${bookId}/.json")
        }
    }

    override suspend fun addBook(userId: String, model: BookDataModel) =
        httpClient.addToken.patch {
            url {
                path("users/${userId}/books/${model.bookId}.json")
                setBody(
                    model
                )
            }
        }

    override suspend fun editBook(userId: String, model: BookDataModel) =
        httpClient.addToken.patch {
            url {
                path("users/${userId}/books/${model.bookId}.json")
                setBody(
                    model
                )
            }
        }

    override suspend fun deleteBook(userId: String, bookId: String) =
        httpClient.addToken.delete {
            url {
                path("users/${userId}/books/${bookId}.json")
            }
        }

}