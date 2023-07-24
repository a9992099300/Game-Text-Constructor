package com.a9992099300.gameTextConstructor.data.books.models
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChapterDataModel(
    @SerialName("chapterId") val chapterId: String,
    @SerialName("title")  val title: String,
    @SerialName("description")  val description: String,
    @SerialName("imageUrl")  val imageUrl: String,
    @SerialName("deletable")  val deletable: Boolean,
) {
//    fun mapToUI() = BookUiModel(
//        this.bookId,
//        this.userIdOwner,
//        this.title,
//        this.description,
//        this.category,
//        this.imageUrl,
//        this.createdDate,
//        this.deletable
//    )
}