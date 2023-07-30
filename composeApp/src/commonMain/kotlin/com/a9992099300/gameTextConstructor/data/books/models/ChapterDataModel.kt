package com.a9992099300.gameTextConstructor.data.books.models

import com.a9992099300.gameTextConstructor.ui.screen.models.ChapterUIModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChapterDataModel(
    @SerialName("chapterId") val chapterId: String,
    @SerialName("chapterNumber") val chapterNumber: Int,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("imageUrl") val imageUrl: String,
    @SerialName("deletable") val deletable: Boolean,
) {

    fun mapToUI() = ChapterUIModel(
        this.chapterId,
        this.chapterNumber,
        this.title,
        this.description,
        this.imageUrl,
        this.deletable,
        false
    )

    companion object {
        fun createEmptyChapter(bookId: String, chapterNumber: Int = 1): ChapterDataModel {
            val chapterId = "${bookId}_${chapterNumber}"
           return ChapterDataModel(
                chapterId = chapterId,
                chapterNumber = chapterNumber,
                title = "Глава $chapterNumber",
                description = "Описание главы",
                imageUrl = "",
                deletable = false
            )
        }
    }

}