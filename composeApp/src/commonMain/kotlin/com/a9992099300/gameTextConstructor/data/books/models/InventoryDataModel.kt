package com.a9992099300.gameTextConstructor.data.books.models

import com.a9992099300.gameTextConstructor.ui.screen.models.InventoryUIModel
import com.a9992099300.gameTextConstructor.ui.screen.models.TypeInventory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InventoryDataModel(
    @SerialName("bookId") val bookId: String,
    @SerialName("inventoryId") val inventoryId: String,
    @SerialName("title") val title: String,
    @SerialName("defaultQuantity") val defaultQuantity: Int,
    @SerialName("typeInventory") val typeInventory: String,
    @SerialName("description") val description: String,
    @SerialName("imageUrl") val imageUrl: String,
) {
    fun mapToUI() = InventoryUIModel(
        this.bookId,
        this.inventoryId,
        this.title,
        this.defaultQuantity,
        when (typeInventory) {
            TypeInventory.THING.value -> TypeInventory.THING
            TypeInventory.CHARACTERISTIC.value -> TypeInventory.CHARACTERISTIC
       //     TypeInventory.ENEMY.value -> TypeInventory.ENEMY
            else -> TypeInventory.THING
        },
        this.description,
        this.imageUrl,
    )
}
