package com.a9992099300.gameTextConstructor.data.books.repository.inventory

import com.a9992099300.gameTextConstructor.data.books.models.InventoryDataModel
import com.a9992099300.gameTextConstructor.data.books.services.inventary.InventoryService
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import com.a9992099300.gameTextConstructor.data.common.allowRequest
import com.a9992099300.gameTextConstructor.data.common.request
import com.a9992099300.gameTextConstructor.data.common.simpleRequest
import io.github.xxfast.kstore.KStore

class InventoryRepositoryImpl(
    private val inventoryService: InventoryService,
    private val store: KStore<SavedAuth>
) : InventoryRepository {

    override suspend fun getInventories(
        bookId: String
    ): Result<List<InventoryDataModel>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId?.let { inventoryService.getInventories(it,  bookId) }
    }

    override suspend fun addInventory(bookId: String, model: InventoryDataModel): Result<InventoryDataModel>  = request {
            val userId = store.get()?.firstOrNull()?.localId
            userId?.let {
                inventoryService.addInventory(
                    userId = it,
                    bookId = bookId,
                    inventoryDataModel = model
                )
            }
        }

    override suspend fun getInventoryIds(bookId: String): Result<List<String>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest{
            inventoryService.getInventoryIds(it, bookId)
        }
    }

    override suspend fun deleteInventory(bookId: String, inventoryId: String): Result<Unit> = request{
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest{
            inventoryService.deleteInventory(it, bookId, inventoryId)
        }
    }
}


