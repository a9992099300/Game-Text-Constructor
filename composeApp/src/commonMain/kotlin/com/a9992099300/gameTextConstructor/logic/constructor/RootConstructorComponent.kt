package com.a9992099300.gameTextConstructor.logic.constructor

import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.child.ProfileConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.listBooks.ListBookConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.menu.MenuConstructorComponent
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootConstructorComponent {

    val childStack: Value<ChildStack<*, Child>>

    val menuConstructorComponent: MenuConstructorComponent

    sealed class Child {
        data class ListBooks(val component: ListBookConstructorComponent) : Child()
        data class Book(val component: BookConstructorComponent) : Child()
        data class Profile(val component: ProfileConstructorComponent) : Child()
    }

}