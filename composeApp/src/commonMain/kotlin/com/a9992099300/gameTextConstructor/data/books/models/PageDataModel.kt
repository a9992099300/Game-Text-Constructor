package com.a9992099300.gameTextConstructor.data.books.models
import com.a9992099300.gameTextConstructor.ui.screen.models.PageUIModel
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

    fun mapToUI() = PageUIModel(
        this.pageId,
        this.title,
        this.description,
        this.imageUrl,
        this.deletable,
        false
    )

    companion object {
        fun createEmptyPage(sceneId: String, pageNumber: Int = 1) =
            PageDataModel(
                pageId = "${sceneId}_$pageNumber",
                title = "Страница $pageNumber",
                description = "Описание страницы",
                imageUrl = "",
                deletable = false
            )
    }
}