package com.a9992099300.gameTextConstructor.logic.constructor.rootBook

import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponentImpl
import com.a9992099300.gameTextConstructor.ui.screen.models.BookUiModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class RootBookConstructorComponentImpl(
    componentContext: ComponentContext,
    private val book: BookUiModel,
    private val popBack: () -> Unit
): RootBookConstructorComponent, ComponentContext by componentContext{

    private val navigation = StackNavigation<Configuration>()

    private fun book(componentContext: ComponentContext): BookConstructorComponentImpl =
        BookConstructorComponentImpl(
            componentContext = componentContext,
            book = book,
            popBack = {
                popBack.invoke()
            }
        )

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Configuration.Book,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val childStack: Value<ChildStack<*, RootBookConstructorComponent.Child>> = stack

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): RootBookConstructorComponent.Child =
        when (configuration) {
            is Configuration.Book -> RootBookConstructorComponent.Child.Book(
                book(componentContext)
            )
        }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Book : Configuration()
    }
}