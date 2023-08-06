package com.a9992099300.gameTextConstructor.data.books.models
import com.a9992099300.gameTextConstructor.ui.screen.models.SceneUIModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SceneDataModel(
    @SerialName("bookId") val bookId: String,
    @SerialName("chapterId") val chapterId: String,
    @SerialName("sceneId") val sceneId: String,
    @SerialName("sceneNumber") val sceneNumber: Int,
    @SerialName("title")  val title: String,
    @SerialName("description")  val description: String,
    @SerialName("imageUrl")  val imageUrl: String,
    @SerialName("deletable")  val deletable: Boolean,
) {

    fun mapToUI() = SceneUIModel(
        this.bookId,
        this.chapterId,
        this.sceneId,
        this.sceneNumber,
        this.title,
        this.description,
        this.imageUrl,
        this.deletable,
        false
    )

}