package com.a9992099300.gameTextConstructor.logic.constructor.createPage

import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.ui.screen.models.ChapterUIModel
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

class RootCreatePageComponentImpl(
    private val componentContext: ComponentContext,
    private val bookId: String,
    private val onPageEdited: () -> Unit,
): ComponentContext by componentContext, RootCreatePageComponent {

    override val pageState: StateFlow<ChapterUIModel>
        get() = TODO("Not yet implemented")

    override fun changeTitle(title: String) {
        TODO("Not yet implemented")
    }

    override fun changeNumber(title: String) {
        TODO("Not yet implemented")
    }

    override fun changeDescription(description: String) {
        TODO("Not yet implemented")
    }

    override fun addOrEditPage() {
        TODO("Not yet implemented")
    }

    override fun resetError() {
        TODO("Not yet implemented")
    }

    override fun deleteChapter() {
        TODO("Not yet implemented")
    }

    override val onBack: () -> Unit
        get() = TODO("Not yet implemented")
    override val stateUi: StateFlow<StateUi<Unit>>
        get() = TODO("Not yet implemented")

}