package com.a9992099300.gameTextConstructor.logic.constructor

import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.child.ProfileConstructorComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.listBooks.ListBookConstructorComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.menu.MenuConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.menu.MenuConstructorComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.menu.models.ItemModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class RootConstructorComponentImpl constructor(
    val componentContext: ComponentContext,
) : RootConstructorComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    private fun listBooks(componentContext: ComponentContext): ListBookConstructorComponentImpl =
        ListBookConstructorComponentImpl(
            componentContext = componentContext
        )

    private fun book(componentContext: ComponentContext): BookConstructorComponentImpl =
        BookConstructorComponentImpl(
            componentContext = componentContext
        )

    private fun profile(componentContext: ComponentContext): ProfileConstructorComponentImpl =
        ProfileConstructorComponentImpl(
            componentContext = componentContext
        )

    override val menuConstructorComponent: MenuConstructorComponent =
        MenuConstructorComponentImpl(
            componentContext,
            openScreen = {
                when (it) {
                    is ItemModel.Profile -> navigation.replaceCurrent(Configuration.Profile)
                    is ItemModel.ListBooks -> navigation.replaceCurrent(Configuration.ListBooks)
                    else -> { }
                }
            }
        )

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Configuration.Book,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val childStack: Value<ChildStack<*, RootConstructorComponent.Child>> = stack

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): RootConstructorComponent.Child =
            when (configuration) {
                is Configuration.ListBooks -> RootConstructorComponent.Child.ListBooks(
                    listBooks(componentContext)
                )
                is Configuration.Book -> RootConstructorComponent.Child.Book(
                    book(componentContext)
                )
                is Configuration.Profile -> RootConstructorComponent.Child.Profile(
                    profile(componentContext)
                )
            }

    private sealed class Configuration : Parcelable {

        @Parcelize
        object Profile : Configuration()

        @Parcelize
        object ListBooks : Configuration()

        @Parcelize
        object Book : Configuration()
    }

}

