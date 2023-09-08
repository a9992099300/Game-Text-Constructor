package com.a9992099300.gameTextConstructor.ui.screen.models

import com.a9992099300.gameTextConstructor.data.books.models.ChapterDataModel


data class ChapterUIModel(
    val bookId: String = "",
    val chapterId: String = "",
    val chapterNumber: Int = 0,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val deletable: Boolean = false,
    val selected: Boolean = true,
) {

    fun mapToData(bookId: String, chapterId: String) = ChapterDataModel(
        bookId,
        "${bookId}_${chapterId}",
        this.chapterNumber,
        this.title,
        this.description,
        this.imageUrl,
        true
    )

    fun mapToData() = ChapterDataModel(
        this.bookId,
        this.chapterId,
        this.chapterNumber,
        this.title,
        this.description,
        this.imageUrl,
        true
    )
}