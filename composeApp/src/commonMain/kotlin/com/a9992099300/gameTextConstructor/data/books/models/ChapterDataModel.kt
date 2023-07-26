package com.a9992099300.gameTextConstructor.data.books.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChapterDataModel(
    @SerialName("chapterId") val chapterId: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("imageUrl") val imageUrl: String,
    @SerialName("deletable") val deletable: Boolean,
) {

    companion object {
        fun createEmptyChapter(bookId: String): ChapterDataModel {
            val chapterId = "${bookId}_0"
           return ChapterDataModel(
                chapterId = chapterId,
                title = "",
                description = "",
                imageUrl = "",
                deletable = false
            )
        }
    }

}