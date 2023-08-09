package com.a9992099300.gameTextConstructor.data.books.repository.inventory

import com.a9992099300.gameTextConstructor.data.books.models.InventoryDataModel
import com.a9992099300.gameTextConstructor.data.common.Result

interface InventoryRepository {

    suspend fun getInventories(bookId: String) : Result<List<InventoryDataModel>>

    suspend fun addInventory(bookId: String, model: InventoryDataModel) : Result<InventoryDataModel>

    suspend fun getInventoryIds(bookId: String): Result<List<String>>

    suspend fun deleteInventory(bookId: String, inventoryId: String): Result<Unit>

}