package com.a9992099300.gameTextConstructor.data.books.services.scene

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

class ScenesServiceImpl(
    private val httpClient: HttpClientWrapper
) : ScenesService {

    private val builderJson = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    override suspend fun addScene(
        userId: String,
        model: SceneDataModel
    ) = httpClient.addToken.patch {
        url {
            path("users/${userId}/userScenes/books/${model.bookId}/chapters/${model.chapterId}/scenes/${model.sceneId}.json")
            setBody(
                model
            )
        }
    }

    override suspend fun getScenes(
        userId: String,
        bookId: String,
        chapterId: String
    ): List<SceneDataModel> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userScenes/books/${bookId}/chapters/${chapterId}/scenes.json")
            }
        }
        val scenes: MutableList<SceneDataModel> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject? = builderJson.decodeFromString(stringBody)


        if (jsonObject != null) {
            for (i in jsonObject) {
                val book = builderJson.decodeFromString<SceneDataModel?>(i.value.toString())
                book?.let { scenes.add(it) }
            }
        }

        return scenes.toList()
    }

    override suspend fun getSceneIds(userId: String, bookId: String, chapterId: String): List<String> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userScenes/books/${bookId}/chapters/${chapterId}/scenes.json")
            }
            url.parameters.append("shallow", "true")
        }
        val scenes: MutableList<String> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject? = builderJson.decodeFromString(stringBody)

        if (jsonObject != null) {
            for (i in jsonObject) {
                scenes.add(i.key)
            }
        }
        return scenes.toList()
    }

    override suspend fun deleteScene(userId: String, bookId: String, chapterId: String, sceneId: String): HttpResponse {
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userScenes/books/${bookId}/chapters/${chapterId}/scenes/${sceneId}.json")
            }
        }
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userChapters/books/${bookId}/chapters/${chapterId}/scenes/${sceneId}.json")
            }
        }
        return httpClient.addToken.delete {
            url {
                path("users/${userId}/userPages/books/${bookId}/chapters/${chapterId}/scenes/${sceneId}.json")
            }
        }
    }
}