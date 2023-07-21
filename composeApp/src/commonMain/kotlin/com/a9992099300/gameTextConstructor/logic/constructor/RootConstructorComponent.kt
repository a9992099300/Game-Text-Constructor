package com.a9992099300.gameTextConstructor.logic.constructor

import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.createBook.CreateBookConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.editBook.EditBookConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.profile.ProfileConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.listBooks.ListBookConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.menu.MenuConstructorComponent
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootConstructorComponent {

    val pageStack: Value<ChildStack<*, Page>>

    val menuConstructorComponent: MenuConstructorComponent

    sealed class Page {
        data class ListBooks(val component: ListBookConstructorComponent) : Page()
        data class Book(val component: BookConstructorComponent) : Page()
        data class Profile(val component: ProfileConstructorComponent) : Page()
        data class CreateBook(val component: CreateBookConstructorComponent) : Page()
        data class EditBook(val component: EditBookConstructorComponent) : Page()
    }

}