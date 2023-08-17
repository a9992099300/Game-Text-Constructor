package com.a9992099300.gameTextConstructor.data.books.services.page

import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel
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

class PageServiceImpl(
    private val httpClient: HttpClientWrapper
) : PageService {

    private val builderJson = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    override suspend fun addPage(
        userId: String,
        model: PageDataModel
    ) = httpClient.addToken.patch {
        url {
            path("users/${userId}/userPages/books/${model.bookId}/chapters/${model.chapterId}/scenes/${model.sceneId}/${model.pageId}.json.json")
            setBody(
                model
            )
        }
    }

    override suspend fun getPages(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
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

    override suspend fun getPage(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String
    ) =  httpClient.addToken.get {
        url {
            path("users/${userId}/userScenes/books/${bookId}/chapters/${chapterId}/scenes/${sceneId}/pages/${pageId}.json")
        }
    }

    override suspend fun getPageIds(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String
    ): List<String> {
        val httpResponseAction: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userPages/books/${bookId}/chapters/${chapterId}/scenes/${sceneId}/page.json")
            }
            url.parameters.append("shallow", "true")
        }
        val pages: MutableList<String> = mutableListOf()
        val stringBody: String = httpResponseAction.body()
        val jsonObject: JsonObject? = builderJson.decodeFromString(stringBody)

        if (jsonObject != null) {
            for (i in jsonObject) {
                val page = builderJson.decodeFromString<String?>(i.value.toString())
                page?.let { pages.add(it) }
            }
        }

        return pages.toList()
    }

    override suspend fun deletePage(
        userId: String,
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String
    ) = httpClient.addToken.delete {
        url {
            path("users/${userId}/userPages/books/${bookId}/chapters/${chapterId}/scenes/${sceneId}.json")
        }
    }

}