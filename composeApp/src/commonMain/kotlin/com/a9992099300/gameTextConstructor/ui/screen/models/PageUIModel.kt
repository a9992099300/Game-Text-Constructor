package com.a9992099300.gameTextConstructor.ui.screen.models

import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.data.books.models.ActionPage
import com.a9992099300.gameTextConstructor.data.books.models.ConditionMark
import com.a9992099300.gameTextConstructor.data.books.models.ItemPage
import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel

data class PageUIModel(
    val bookId: String = "",
    val chapterId: String = "",
    val sceneId: String = "",
    val pageId: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val action: ItemPageUi = ItemPageUi(),
    val deletable: Boolean = false,
    val selected: Boolean = false,
) {
    fun mapToData(bookId: String, chapterId: String, sceneId: String, pageId: String) =
        PageDataModel(
            bookId = bookId,
            chapterId = chapterId,
            sceneId = sceneId,
            pageId = "${sceneId}_${pageId}",
            title = this.title,
            inputArguments = listOf(),
            description = this.description,
            addDescription = listOf(),
            imageUrl = this.imageUrl,
            deletable = true,
            items = action.mapToData()
        )

    fun mapToData() = PageDataModel(
        bookId = this.bookId,
        chapterId = this.chapterId,
        sceneId = this.sceneId,
        pageId = this.pageId,
        title = this.title,
        inputArguments = listOf(),
        description = this.description,
        addDescription = listOf(),
        imageUrl = this.imageUrl,
        deletable = true,
        items = action.mapToData()
    )
}

data class ItemPageUi(
    val id: String = "",
    val description: String = "",
    val action: ActionTypeUI = ActionTypeUI.Move()
) {

    fun mapToData() =
        ItemPage(
            actionId = id,
            description = description,
            action = action.mapToData()
        )
}

sealed class ActionTypeUI {

    companion object {
        val defaultListAction =
            listOf<ActionTypeUI>(
                Move(),
                RandomMove(),
                ConditionMove()
            )
    }

    abstract fun mapToData(): ActionPage

    abstract val selected: Boolean
    abstract val title: String

    data class Move(
        override val title: String = MainRes.string.action_move,
        val startDestination: String = "",
        val endDestination:  List<ItemNavigation> = listOf(),
        override val selected: Boolean = false,
    ) : ActionTypeUI() {
        override fun mapToData(): ActionPage =
            ActionPage.Move(
                startDestination = startDestination,
                endDestination = "endDestination"
            )
    }

    data class ItemNavigation(
        val chapterId: String,
        val chapterTitle: String,
        val sceneId: String,
        val sceneTitle: String,
        val pageID: String,
        val pageTitle: String,
    )


    data class RandomMove(
        override val title: String = MainRes.string.action_random_move,
        val startDestination: String = "",
        val endDestinations: List<String> = listOf(),
        override val selected: Boolean = false,
    ) : ActionTypeUI() {
        override fun mapToData(): ActionPage =
            ActionPage.RandomMove(
                startDestination = startDestination,
                endDestinations = endDestinations
            )
    }


    data class ConditionMove(
        override val title: String = MainRes.string.action_condition_move,
        val conditionMark: ConditionMark = ConditionMark.NOT_USE,
        val nameThing: String = "",
        val quantity: Int = 0,
        val startDestination: String = "",
        val endDestinationIf: String = "",
        val endDestinationElse: String = "",
        override val selected: Boolean = false,
    ) : ActionTypeUI() {
        override fun mapToData(): ActionPage =
            ActionPage.ConditionMove(
                conditionMark = conditionMark,
                nameThing = nameThing,
                quantity = quantity,
                startDestination = startDestination,
                endDestinationIf = endDestinationIf,
                endDestinationElse = endDestinationElse
            )
    }
}