package com.a9992099300.gameTextConstructor.data.books.services.inventary

import com.a9992099300.gameTextConstructor.data.books.models.InventoryDataModel
import io.ktor.client.statement.HttpResponse

interface InventoryService{

     suspend fun addInventory(
          userId: String,
          bookId: String,
          inventoryDataModel: InventoryDataModel
     ) : HttpResponse

     suspend fun getInventories(userId: String, bookId: String) : List<InventoryDataModel>

     suspend fun getInventoryIds(userId: String, bookId: String): List<String>

     suspend fun deleteInventory(userId: String, bookId: String, inventoryId: String): HttpResponse
}