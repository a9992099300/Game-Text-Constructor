package com.a9992099300.gameTextConstructor.data.books.services.task

import com.a9992099300.gameTextConstructor.data.books.models.TaskDataModel
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

class TaskServiceImpl(
    private val httpClient: HttpClientWrapper
) : TaskService {

    private val builderJson = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    override suspend fun addTask(
        userId: String,
        bookId: String,
        tasksDataModel: TaskDataModel
    ) = httpClient.addToken.patch {
        url {
            path("users/${userId}/userTasks/books/${bookId}/tasks/${tasksDataModel.taskId}.json")
            setBody(
                tasksDataModel
            )
        }
    }

    override suspend fun getTasks(userId: String, bookId: String): List<TaskDataModel> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userTasks/books/${bookId}/tasks.json")
            }
        }
        val inventories: MutableList<TaskDataModel> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject? = builderJson.decodeFromString(stringBody)


        if (jsonObject != null) {
            for (i in jsonObject) {
                val inventory = builderJson.decodeFromString<TaskDataModel?>(i.value.toString())
                inventory?.let { inventories.add(it) }
            }
        }

        return inventories.toList()
    }

    override suspend fun getTaskIds(userId: String, bookId: String): List<String> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userTasks/books/${bookId}/tasks.json")
                url.parameters.append("shallow", "true")
            }
        }
        val tasks: MutableList<String> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject? = builderJson.decodeFromString(stringBody)

        if (jsonObject != null) {
            for (i in jsonObject) {
                tasks.add(i.key)
            }
        }
        return tasks.toList()
    }

    override suspend fun deleteTask(
        userId: String,
        bookId: String,
        taskId: String
    ): HttpResponse  =
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userTasks/books/${bookId}/tasks/${taskId}.json")
            }
        }



}