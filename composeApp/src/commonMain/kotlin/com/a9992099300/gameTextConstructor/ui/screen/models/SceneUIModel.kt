package com.a9992099300.gameTextConstructor.ui.screen.models

import com.a9992099300.gameTextConstructor.data.books.models.SceneDataModel

data class SceneUIModel(
    val bookId: String = "",
    val chapterId: String = "",
    val sceneId: String = "",
    val sceneNumber: Int = 0,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val deletable: Boolean = true,
    val selected: Boolean = false,
) {
    fun mapToData(bookId: String, chapterId: String, sceneId: String) = SceneDataModel(
        bookId,
        chapterId,
        "${chapterId}_${sceneId}",
        this.sceneNumber,
        this.title,
        this.description,
        this.imageUrl,
        true
    )

    fun mapToData() = SceneDataModel(
        this.bookId,
        this.chapterId,
        this.sceneId,
        this.sceneNumber,
        this.title,
        this.description,
        this.imageUrl,
        true
    )
}