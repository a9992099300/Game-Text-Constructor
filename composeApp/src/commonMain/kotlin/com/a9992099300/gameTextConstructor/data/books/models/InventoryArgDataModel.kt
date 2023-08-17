package com.a9992099300.gameTextConstructor.data.books.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InventoryArgDataModel(
    @SerialName("inventoryDataModel") val inventoryDataModel: InventoryDataModel,
    @SerialName("value") val value: Int,
) {

}
