package com.a9992099300.gameTextConstructor.ui.screen.models

import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel

data class PageUIModel(
    val bookId: String = "",
    val chapterId: String = "",
    val sceneId: String = "",
    val pageId: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val deletable: Boolean = false,
    val selected: Boolean = false,
) {
    fun mapToData(bookId: String, chapterId: String, sceneId: String, pageId: String) = PageDataModel(
       bookId = bookId,
       chapterId = chapterId,
       sceneId = sceneId,
        pageId ="${sceneId}_${pageId}",
        title = this.title,
        inputArguments = listOf(),
        description = this.description,
        addDescription = listOf(),
        imageUrl = this.imageUrl,
        deletable = true,
        items = listOf()
    )

    fun mapToData() = PageDataModel(
        bookId = this.bookId,
        chapterId = this.chapterId,
        sceneId = this.sceneId,
        pageId = this.pageId,
        title = this.title,
        inputArguments = listOf(),
        description = this.description,
        addDescription = listOf(),
        imageUrl = this.imageUrl,
        deletable = true,
        items = listOf()
    )
}