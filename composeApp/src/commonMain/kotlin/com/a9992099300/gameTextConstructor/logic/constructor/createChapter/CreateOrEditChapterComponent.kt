package com.a9992099300.gameTextConstructor.logic.constructor.createChapter

import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.ui.screen.models.ChapterUIModel
import kotlinx.coroutines.flow.StateFlow

interface CreateOrEditChapterComponent: BaseComponent<Unit> {

    val chapterState: StateFlow<ChapterUIModel>

    val editedChapterModel: ChapterUIModel

    val chapterIds: StateFlow<List<String>>

    val snackBar: StateFlow<String>

    fun changeTitle(title: String)

    fun changeNumber(title: String)

    fun changeDescription(description: String)

    fun addOrEditChapter()

    fun resetError()

    fun deleteChapter()

}