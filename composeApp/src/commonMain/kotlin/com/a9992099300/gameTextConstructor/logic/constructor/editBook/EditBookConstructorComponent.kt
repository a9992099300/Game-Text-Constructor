package com.a9992099300.gameTextConstructor.logic.constructor.editBook

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.ui.screen.models.CategoryUiModel
import com.a9992099300.gameTextConstructor.utils.TypeCategory
import kotlinx.coroutines.flow.StateFlow

interface EditBookConstructorComponent {

    val stateUi: StateFlow<StateUi<Unit>>
    val stateCategory: StateFlow<List<CategoryUiModel>>
    val titleBook: StateFlow<String>
    val descriptionBook: StateFlow<String>
    val dataModel: StateFlow<BookDataModel?>

    fun chooseCategory(type: TypeCategory)

    fun changeTitle(title: String)

    fun changeDescription(description: String)

    fun editBook()

    fun deleteBook()

    fun onBackClicked()

}