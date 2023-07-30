package com.a9992099300.gameTextConstructor.data.books.models
import com.a9992099300.gameTextConstructor.ui.screen.models.SceneUIModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SceneDataModel(
    @SerialName("sceneId") val sceneId: String,
    @SerialName("title")  val title: String,
    @SerialName("description")  val description: String,
    @SerialName("imageUrl")  val imageUrl: String,
    @SerialName("deletable")  val deletable: Boolean,
) {

    fun mapToUI() = SceneUIModel(
        this.sceneId,
      //  this.chapterNumber,
        this.title,
        this.description,
        this.imageUrl,
        this.deletable,
        false
    )


    companion object {
        fun createEmptyScene(chapterId: String, sceneNumber: Int = 1): SceneDataModel {
            val sceneId = "${chapterId}_$sceneNumber"
           return SceneDataModel(
                sceneId = sceneId,
                title = "Сцена $sceneNumber",
                description = "Описнаие сцены",
                imageUrl = "",
                deletable = false
            )
        }
    }

}