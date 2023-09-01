package com.a9992099300.gameTextConstructor.data.books.services.actions

import com.a9992099300.gameTextConstructor.data.books.models.ItemPage
import com.a9992099300.gameTextConstructor.data.common.ktor.AUTH
import com.a9992099300.gameTextConstructor.data.common.ktor.HttpClientWrapper
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

class ActionServiceImpl(
    private val httpClient: HttpClientWrapper
) : ActionService {

    private val builderJson = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    override suspend fun addAction(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String,
        model: ItemPage,
        token: String
    ) = httpClient.addToken.patch {
        url {
            path("users/${userId}/userPages/books/${bookId}/chapters/${chapterId}/scenes/${sceneId}/pages/${pageId}/items/${model.actionId}.json")
            url.parameters.append(AUTH, token)
            setBody(
                model
            )
        }
    }

    override suspend fun getActions(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String
    ): List<ItemPage> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userPages/books/${bookId}/chapters/${chapterId}/scenes/${sceneId}/pages/${pageId}/items.json")
            }
        }
        val pages: MutableList<ItemPage> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject? = builderJson.decodeFromString(stringBody)


        if (jsonObject != null) {
            for (i in jsonObject) {
                val page = builderJson.decodeFromString<ItemPage?>(i.value.toString())
                page?.let { pages.add(it) }
            }
        }

        return pages.toList()
    }

    override suspend fun getAction(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String,
        actionId: String
    ): HttpResponse = httpClient.addToken.get {
        url {
            path("users/${userId}/userPages/books/${bookId}/chapters/${chapterId}/scenes/${sceneId}/pages/${pageId}/items/${actionId}.json")
        }
    }

    override suspend fun getActionIds(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String
    ): List<String> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userPages/books/${bookId}/chapters/${chapterId}/scenes/${sceneId}/pages/${pageId}/items.json")
            }
            url.parameters.append("shallow", "true")
        }
        val items: MutableList<String> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject? = builderJson.decodeFromString(stringBody)

        if (jsonObject != null) {
            for (i in jsonObject) {
                items.add(i.key)
            }
        }
        return items.toList()
    }

    override suspend fun deleteAction(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String,
        actionId: String,
        token: String
    ): HttpResponse {
        TODO("Not yet implemented")
    }

}