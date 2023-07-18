package com.a9992099300.gameTextConstructor.logic.constructor.listBooks

import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import kotlinx.coroutines.flow.StateFlow

interface ListBookConstructorComponent {

    val stateUi: StateFlow<StateUi<Unit>>

    val books: StateFlow<List<BookDataModel>>

  //  val dialog: Value<ChildSlot<*, CreateBookConstructorComponent>>
    fun getBooksList()

    fun createNewBook()

    sealed class Child {
        object CreateNewBook : Child()
    }
}