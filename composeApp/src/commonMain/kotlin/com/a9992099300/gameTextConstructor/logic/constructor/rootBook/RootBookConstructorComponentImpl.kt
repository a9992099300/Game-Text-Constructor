package com.a9992099300.gameTextConstructor.logic.constructor.rootBook

import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.constructor.action.CreateOrEditActionComponent
import com.a9992099300.gameTextConstructor.logic.constructor.action.CreateOrEditActionComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.createChapter.CreateOrEditChapterComponent
import com.a9992099300.gameTextConstructor.logic.constructor.createChapter.CreateOrEditChapterComponentImpl
import com.a9992099300.gameTextConstructor.logic.constructor.createPage.CreateOrEditPageComponent
import com.a9992099300.gameTextConstructor.logic.constructor.createPage.CreateOrEditPageComponentImpl
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
    private val bookId: String,
    private val popBack: () -> Unit
) : RootBookConstructorComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    private fun book(componentContext: ComponentContext): BookConstructorComponent =
        BookConstructorComponentImpl(
            componentContext = componentContext,
            bookId = bookId,
            popBack = {
                popBack.invoke()
            },
            onCreateChapter = {
                navigation.push(Configuration.CreateOrEditChapter(""))
            },
            onEditChapter = {
                navigation.push(Configuration.CreateOrEditChapter(it))
            },
            onCreateScene = {
                navigation.push(
                    Configuration.CreateOrEditScene(
                        chapterId = it
                    )
                )
            },
            onEditScene = { chapter , scene ->
                navigation.push(
                    Configuration.CreateOrEditScene(
                        sceneId = scene,
                        chapterId = chapter
                    )
                )
            },
            onCreateOrEditPage = { chapterId, sceneId, pageId ->
                navigation.push(
                    Configuration.CreateOrEditPage(
                        chapterId = chapterId,
                        sceneId = sceneId,
                        pageId = pageId
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
            bookId = bookId,
            onChapterEdited = {
                navigation.pop {
                    (stack.value.active.instance as? RootBookConstructorComponent.Child.Book)
                        ?.component?.loadChapters()
                }
            },
            onBack = {
                navigation.pop()
            },
            chapterId = chapterId
        )

    private fun createScene(
        componentContext: ComponentContext,
        sceneId: String,
        chapterId: String
    ): CreateOrEditScenesComponent =
        CreateOrEditScenesComponentImpl(
            componentContext = componentContext,
            bookId = bookId,
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
            sceneId = sceneId
        )

    private fun createInventory(
        componentContext: ComponentContext,
    ): InventoryComponent =
        InventoryComponentImpl(
            componentContext = componentContext,
            bookId = bookId,
            inventoryRepository = Inject.instance(),
            onBack = {
                navigation.pop()
            },
        )

    private fun createPage(
        componentContext: ComponentContext,
        chapterId: String,
        sceneId: String,
        pageId: String,
    ): CreateOrEditPageComponent =
        CreateOrEditPageComponentImpl(
            componentContext = componentContext,
            bookId = bookId,
            chapterId = chapterId,
            sceneId = sceneId,
            pageId = pageId,
            onBack = {
                navigation.pop()
            },
            openCreateAction = { actionId ->
                navigation.push(
                    Configuration.CreateOrEditAction(
                        chapterId,
                        sceneId,
                        pageId,
                        actionId
                    )
                )
            },
            onSaveChanged = {
                navigation.pop {
                    (stack.value.active.instance as? RootBookConstructorComponent.Child.Book)
                        ?.component?.loadPages(sceneId)
                }
            }
        )

    private fun createAction(
        componentContext: ComponentContext,
        chapterId: String,
        sceneId: String,
        pageId: String,
        actionId: String
    ): CreateOrEditActionComponent =
        CreateOrEditActionComponentImpl(
            componentContext = componentContext,
            bookId = bookId,
            chapterId = chapterId,
            sceneId = sceneId,
            pageId = pageId,
            actionId = actionId,
            onBack = {
                 navigation.pop()
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

            is Configuration.CreateOrEditChapter -> RootBookConstructorComponent.Child.CreateOrEditChapter(
                createChapter(componentContext, configuration.chapterId)
            )

            is Configuration.CreateOrEditScene -> RootBookConstructorComponent.Child.CreateOrEditScene(
                createScene(componentContext, configuration.sceneId, configuration.chapterId)
            )

            is Configuration.Inventory -> RootBookConstructorComponent.Child.Inventory(
                createInventory(componentContext)
            )

            is Configuration.CreateOrEditPage -> RootBookConstructorComponent.Child.CreateOrEditPage(
                createPage(
                    componentContext,
                    configuration.chapterId,
                    configuration.sceneId,
                    configuration.pageId
                )
            )

            is Configuration.CreateOrEditAction -> RootBookConstructorComponent.Child.CreateOrEditAction(
                createAction(
                    componentContext,
                    configuration.chapterId,
                    configuration.sceneId,
                    configuration.pageId,
                    configuration.actionId
                )
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

        @Parcelize
        data class CreateOrEditPage(
            val chapterId: String,
            val sceneId: String,
            val pageId: String,
        ) : Configuration()

        @Parcelize
        data class CreateOrEditAction(
            val chapterId: String,
            val sceneId: String,
            val pageId: String,
            val actionId: String,
        ) : Configuration()
    }
}