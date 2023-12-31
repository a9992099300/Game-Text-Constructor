package com.a9992099300.gameTextConstructor.logic.constructor.listBooks

import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.ui.screen.models.BookUiModel
import kotlinx.coroutines.flow.StateFlow

interface ListBookConstructorComponent: BaseComponent<Unit> {

    override val stateUi: StateFlow<StateUi<Unit>>

    val books: StateFlow<List<BookUiModel>>

    fun getBooksList()

    fun createNewBook()

    fun editBook(bookId: String)

}