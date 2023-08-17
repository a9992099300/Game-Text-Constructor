package com.a9992099300.gameTextConstructor.logic.constructor.createPage

import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.ui.screen.models.ChapterUIModel
import kotlinx.coroutines.flow.StateFlow

interface RootCreatePageComponent: BaseComponent<Unit> {

    val pageState: StateFlow<ChapterUIModel>

    fun changeTitle(title: String)

    fun changeNumber(title: String)

    fun changeDescription(description: String)

    fun addOrEditPage()

    fun resetError()

    fun deleteChapter()

}