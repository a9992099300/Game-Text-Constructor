package com.a9992099300.gameTextConstructor.logic.constructor.createBook

import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.ui.screen.models.CategoryUiModel
import com.a9992099300.gameTextConstructor.utils.TypeCategory
import kotlinx.coroutines.flow.StateFlow

interface CreateBookConstructorComponent: BaseComponent<Unit> {

    val stateCategory: StateFlow<List<CategoryUiModel>>
    val titleBook: StateFlow<String>
    val descriptionBook: StateFlow<String>

    fun chooseCategory(type: TypeCategory)

    fun changeTitle(title: String)

    fun changeDescription(description: String)

    fun addBook()

}