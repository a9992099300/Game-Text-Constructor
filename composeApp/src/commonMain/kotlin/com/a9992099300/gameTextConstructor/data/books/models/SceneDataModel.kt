package com.a9992099300.gameTextConstructor.data.books.models
import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel.Companion.createEmptyPage
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

    companion object {
        fun createEmptyScene(chapterId: String): SceneDataModel {
            val sceneId = "${chapterId}_0"
           return SceneDataModel(
                sceneId = sceneId,
                title = "",
                description = "",
                imageUrl = "",
                deletable = false
            )
        }
    }

}