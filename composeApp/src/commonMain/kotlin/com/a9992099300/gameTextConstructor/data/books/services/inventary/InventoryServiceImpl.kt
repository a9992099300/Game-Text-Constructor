package com.a9992099300.gameTextConstructor.data.books.services.inventary

import com.a9992099300.gameTextConstructor.data.books.models.InventoryDataModel
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

class InventoryServiceImpl(
    private val httpClient: HttpClientWrapper
) : InventoryService {

    private val builderJson = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    override suspend fun addInventory(
        userId: String,
        bookId: String,
        inventoryDataModel: InventoryDataModel
    ) = httpClient.addToken.patch {
        url {
            path("users/${userId}/userInventories/books/${bookId}/inventories/${inventoryDataModel.inventoryId}.json")
            setBody(
                inventoryDataModel
            )
        }
    }

    override suspend fun getInventories(userId: String, bookId: String): List<InventoryDataModel> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userInventories/books/${bookId}/inventories.json")
            }
        }
        val inventories: MutableList<InventoryDataModel> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject? = builderJson.decodeFromString(stringBody)


        if (jsonObject != null) {
            for (i in jsonObject) {
                val inventory = builderJson.decodeFromString<InventoryDataModel?>(i.value.toString())
                inventory?.let { inventories.add(it) }
            }
        }

        return inventories.toList()
    }

    override suspend fun getInventoryIds(userId: String, bookId: String): List<String> {
        val httpResponse: HttpResponse = httpClient.addToken.get {
            url {
                path("users/${userId}/userInventories/books/${bookId}/inventories.json")
                url.parameters.append("shallow", "true")
            }
        }
        val inventories: MutableList<String> = mutableListOf()
        val stringBody: String = httpResponse.body()
        val jsonObject: JsonObject? = builderJson.decodeFromString(stringBody)

        if (jsonObject != null) {
            for (i in jsonObject) {
                inventories.add(i.key)
            }
        }
        return inventories.toList()
    }

    override suspend fun deleteInventory(
        userId: String,
        bookId: String,
        inventoryId: String
    ): HttpResponse  =
        httpClient.addToken.delete {
            url {
                path("users/${userId}/userInventories/books/${bookId}/inventories/${inventoryId}.json")
            }
        }
}