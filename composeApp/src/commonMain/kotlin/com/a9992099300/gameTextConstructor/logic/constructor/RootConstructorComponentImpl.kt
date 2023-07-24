package com.a9992099300.gameTextConstructor.logic.constructor

import com.a9992099300.gameTextConstructor.data.common.ktor.HttpClientWrapper
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.createBook.CreateBookConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.createBook.CreateBookConstructorComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.editBook.EditBookConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.editBook.EditBookConstructorComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.listBooks.ListBookConstructorComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.menu.MenuConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.menu.MenuConstructorComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.menu.models.ItemModel
import com.a9992099300.gameTextConstructor.logic.constructor.profile.ProfileConstructorComponentImpl
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class RootConstructorComponentImpl constructor(
    val componentContext: ComponentContext,
) : RootConstructorComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()
    private val httpClientWrapper: HttpClientWrapper = Inject.instance()

    private fun listBooks(componentContext: ComponentContext): ListBookConstructorComponentImpl =
        ListBookConstructorComponentImpl(
            componentContext = componentContext,
            onCreateBook = {
                createNewBook()
            },
            onEditBook = {
                editNewBook(it)
            },
            onBack = {}
        )

    private fun createNewBook(
        componentContext: ComponentContext
    ): CreateBookConstructorComponent =
        CreateBookConstructorComponentImpl(
            componentContext = componentContext,
            onBack = {
                popBack()
            },
            onBookEdit = {
                onBookEdited()
            }
        )

    private fun editBook(
        componentContext: ComponentContext,
        config: Configuration.EditBook
    ): EditBookConstructorComponent =
        EditBookConstructorComponentImpl(
            componentContext = componentContext,
            bookId = config.bookId,
            onBack = {
                popBack()
            },
            onBookEdit = {
                onBookEdited()
            }
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
                    is ItemModel.Profile -> navigation.bringToFront(Configuration.Profile)
                    is ItemModel.ListBooks -> navigation.bringToFront(Configuration.ListBooks)
                    is ItemModel.Exit -> httpClientWrapper.logout()
                }
            }
        )

    private fun createNewBook(): Unit = navigation.bringToFront(Configuration.CreateBook)

    private fun editNewBook(bookId: String): Unit =
        navigation.bringToFront(Configuration.EditBook(bookId = bookId))

    private fun popBack(): Unit = navigation.pop()

    private fun onBookEdited(): Unit = navigation.pop {
        (stack.value.active.instance as? RootConstructorComponent.Page.ListBooks)
            ?.component?.getBooksList()
    }

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Configuration.Book,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val pageStack: Value<ChildStack<*, RootConstructorComponent.Page>> = stack

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): RootConstructorComponent.Page =
        when (configuration) {
            is Configuration.ListBooks -> RootConstructorComponent.Page.ListBooks(
                listBooks(componentContext)
            )

            is Configuration.Book -> RootConstructorComponent.Page.Book(
                book(componentContext)
            )

            is Configuration.Profile -> RootConstructorComponent.Page.Profile(
                profile(componentContext)
            )

            is Configuration.CreateBook -> RootConstructorComponent.Page.CreateBook(
                createNewBook(componentContext)
            )

            is Configuration.EditBook -> RootConstructorComponent.Page.EditBook(
                editBook(componentContext, configuration)
            )

        }

    private sealed class Configuration : Parcelable {

        @Parcelize
        object Profile : Configuration()

        @Parcelize
        object ListBooks : Configuration()

        @Parcelize
        object Book : Configuration()

        @Parcelize
        object CreateBook : Configuration()

        @Parcelize
        data class EditBook(val bookId: String) : Configuration()

    }
}

