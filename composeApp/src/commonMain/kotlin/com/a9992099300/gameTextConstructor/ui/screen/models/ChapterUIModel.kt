package com.a9992099300.gameTextConstructor.ui.screen.models

data class ChapterUIModel(
    val chapterId: String,
    val chapterNumber: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val deletable: Boolean,
    val selected: Boolean,
) {
}