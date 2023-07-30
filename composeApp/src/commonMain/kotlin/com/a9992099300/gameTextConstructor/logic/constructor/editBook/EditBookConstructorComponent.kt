package com.a9992099300.gameTextConstructor.logic.constructor.editBook

import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.ui.screen.models.BookUiModel
import com.a9992099300.gameTextConstructor.ui.screen.models.CategoryUiModel
import com.a9992099300.gameTextConstructor.utils.TypeCategory
import kotlinx.coroutines.flow.StateFlow

interface EditBookConstructorComponent: BaseComponent<Unit> {

    val stateCategory: StateFlow<List<CategoryUiModel>>
    val uiModel: StateFlow<BookUiModel>

    fun chooseCategory(type: TypeCategory)

    fun changeTitle(title: String)

    fun changeDescription(description: String)

    fun editBook()

    fun deleteBook()

    fun onEditScenes()

}