package com.a9992099300.gameTextConstructor.logic.constructor.createPage

import com.a9992099300.gameTextConstructor.data.books.models.ItemPage
import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.ui.screen.models.PageUIModel
import kotlinx.coroutines.flow.StateFlow

interface CreateOrEditPageComponent : BaseComponent<Unit> {

    val uiPageModel: StateFlow<PageUIModel>

    val uiActions: StateFlow<List<ItemPage>>

    val pageIds: StateFlow<List<String>>

    fun changeTitle(title: String)

    fun savePage()

    fun changeDescription(description: String)

    fun onCreateAction()

    fun resetError()


}