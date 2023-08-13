package com.a9992099300.gameTextConstructor.ui.screen.models

import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.data.books.models.InventoryDataModel

data class InventoryUIModel(
    val bookId: String = "",
    val inventoryId: String = "",
    val title: String = "",
    val defaultQuantity: Int = 0,
    val typeInventory: TypeInventory,
    val description: String = "",
    val imageUrl: String = "",
    val selected: Boolean = false,
) {
    fun mapToData(bookId: String, type: TypeInventory) = InventoryDataModel(
        bookId,
        inventoryId,
        title,
        defaultQuantity,
        type.value,
        description,
        imageUrl
    )

    fun mapToData(type: TypeInventory) = InventoryDataModel(
        bookId,
        inventoryId,
        title,
        defaultQuantity,
        type.value,
        description,
        imageUrl
    )

}

enum class TypeInventory(val value: String){
    THING("things"),
    CHARACTERISTIC("characteristic"),
//    ENEMY("enemy");
}

object Inventory{
    val listDefaultType = listOf(
        TypeInventoryUiModel(
            typeInventory = TypeInventory.THING,
            title = MainRes.string.inventories,
            selected = false
        ),
        TypeInventoryUiModel(
            typeInventory = TypeInventory.CHARACTERISTIC,
            title = MainRes.string.characteristics,
            selected = false
        ),
    )
}