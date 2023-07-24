package com.a9992099300.gameTextConstructor.data.books.models

import com.a9992099300.gameTextConstructor.ui.screen.models.BookUiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDataModel(
    @SerialName("bookId") val bookId: String,
    @SerialName("userIdOwner") val userIdOwner: String,
    @SerialName("title")  val title: String,
    @SerialName("description")  val description: String,
    @SerialName("category")  val category: String,
    @SerialName("imageUrl")  val imageUrl: String,
    @SerialName("createdDate")  val createdDate: String,
    @SerialName("deletable")  val deletable: Boolean,
) {
    fun mapToUI() = BookUiModel(
        this.bookId,
        this.userIdOwner,
        this.title,
        this.description,
        this.category,
        this.imageUrl,
        this.createdDate,
        this.deletable
    )
}