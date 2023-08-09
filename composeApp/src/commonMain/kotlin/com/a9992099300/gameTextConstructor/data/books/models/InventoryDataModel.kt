package com.a9992099300.gameTextConstructor.data.books.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InventoryDataModel(
    @SerialName("bookId") val bookId: String,
    @SerialName("inventoryId") val inventoryId: String,
    @SerialName("title")  val title: String,
    @SerialName("defaultQuantity")  val defaultQuantity: String,
    @SerialName("typeInventory")  val typeInventory: String,
)

@Serializable
enum class TypeInventory(value: String){
    THING("things"),
    CHARACTERISTIC("characteristic"),
    ENEMY("enemy")
}