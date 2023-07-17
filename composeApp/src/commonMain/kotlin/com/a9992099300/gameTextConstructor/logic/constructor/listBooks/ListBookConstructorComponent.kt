package com.a9992099300.gameTextConstructor.logic.constructor.listBooks

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import kotlinx.coroutines.flow.StateFlow

interface ListBookConstructorComponent {

    val stateUi: StateFlow<StateUi<Unit>>

    val books: StateFlow<List<BookDataModel>>
    fun getBooksList()

    sealed class ListBook {
        object CreateNewBook : ListBook()
    }
}