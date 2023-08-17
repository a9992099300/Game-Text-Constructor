package com.a9992099300.gameTextConstructor.logic.constructor.rootBook

import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.createChapter.CreateOrEditChapterComponent
import com.a9992099300.gameTextConstructor.logic.constructor.createChapter.CreateOrEditChapterComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.createScenes.CreateOrEditScenesComponent
import com.a9992099300.gameTextConstructor.logic.constructor.createScenes.CreateOrEditScenesComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.inventory.InventoryComponent
import com.a9992099300.gameTextConstructor.logic.constructor.inventory.InventoryComponentImpl
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class RootBookConstructorComponentImpl(
    componentContext: ComponentContext,
    private val book: String,
    private val popBack: () -> Unit
) : RootBookConstructorComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()


    private fun book(componentContext: ComponentContext): BookConstructorComponent =
        BookConstructorComponentImpl(
            componentContext = componentContext,
            book = book,
            popBack = {
                popBack.invoke()
            },
            onCreateChapter = {
                navigation.push(Configuration.CreateOrEditChapter(""))
            },
            onEditChapter = {
                navigation.push(Configuration.CreateOrEditChapter(it.chapterId))
            },
            onCreateScene = {
                navigation.push(
                    Configuration.CreateOrEditScene(
                        chapterId = it
                    )
                )
            },
            onEditScene = {
                navigation.push(
                    Configuration.CreateOrEditScene(
                        sceneId = it.sceneId,
                        chapterId = it.chapterId
                    )
                )
            },
            onOpenInventory = {
                navigation.push(
                    Configuration.Inventory
                )
            }
        )

    private fun createChapter(
        componentContext: ComponentContext,
        chapterId: String
    ): CreateOrEditChapterComponent =
        CreateOrEditChapterComponentImpl(
            componentContext = componentContext,
            bookId = book,
            onChapterEdited = {
                navigation.pop {
                    (stack.value.active.instance as? RootBookConstructorComponent.Child.Book)
                        ?.component?.loadChapters()
                }
            },
            onBack = {
                navigation.pop()
            },
            editedChapterModel = chapterId
        )

    private fun createScene(
        componentContext: ComponentContext,
        sceneId: String,
        chapterId: String
    ): CreateOrEditScenesComponent =
        CreateOrEditScenesComponentImpl(
            componentContext = componentContext,
            bookId = book,
            chapterId = chapterId,
            onSceneEdited = {
                navigation.pop {
                    (stack.value.active.instance as? RootBookConstructorComponent.Child.Book)
                        ?.component?.loadScenes(chapterId)
                }
            },
            onBack = {
                navigation.pop()
            },
            editeSceneModel = sceneId
        )

    private fun createInventory(
        componentContext: ComponentContext,
    ): InventoryComponent =
        InventoryComponentImpl(
            componentContext = componentContext,
            bookId = book,
            inventoryRepository = Inject.instance(),
            onBack = {
                navigation.pop()
            },
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

            is Configuration.CreateOrEditChapter -> RootBookConstructorComponent.Child.CreateOrEditChapter(
                createChapter(componentContext, configuration.chapterId)
            )

            is Configuration.CreateOrEditScene -> RootBookConstructorComponent.Child.CreateOrEditScene(
                createScene(componentContext, configuration.sceneId, configuration.chapterId)
            )
            is Configuration.Inventory -> RootBookConstructorComponent.Child.Inventory(
                createInventory(componentContext)
            )
        }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Book : Configuration()

        @Parcelize
        object Inventory : Configuration()

        @Parcelize
        data class CreateOrEditChapter(val chapterId: String) : Configuration()

        @Parcelize
        data class CreateOrEditScene(
            val sceneId: String = "",
            val chapterId: String
        ) : Configuration()
    }
}