package com.a9992099300.gameTextConstructor.data.books.models
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageDataModel(
    @SerialName("pageId") val pageId: String,
    @SerialName("title")  val title: String,
    @SerialName("description")  val description: String,
    @SerialName("imageUrl")  val imageUrl: String,
    @SerialName("deletable")  val deletable: Boolean,
) {
    companion object {
        fun createEmptyPage(sceneId: String) =
            PageDataModel(
                pageId = "${sceneId}_0",
                title = "",
                description = "",
                imageUrl = "",
                deletable = false
            )
    }
}