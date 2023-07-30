package com.a9992099300.gameTextConstructor.ui.screen.models

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.arkivanov.essenty.parcelable.Parcelize


@Parcelize
data class BookUiModel(
    val bookId: String ="",
    val userIdOwner: String ="",
    val title: String ="",
    val description: String ="",
    val category: String ="",
    val imageUrl: String ="",
    val createdDate: String ="",
    val deletable: Boolean = false,
) {
    fun mapToData() = BookDataModel(
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
