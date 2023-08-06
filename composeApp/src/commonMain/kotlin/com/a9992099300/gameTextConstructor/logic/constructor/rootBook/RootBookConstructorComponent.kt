package com.a9992099300.gameTextConstructor.logic.constructor.rootBook

import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.createChapter.CreateOrEditChapterComponent
import com.a9992099300.gameTextConstructor.logic.constructor.createScenes.CreateOrEditScenesComponent
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootBookConstructorComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Book(val component: BookConstructorComponent) : Child()

        data class CreateOrEditChapter(val component: CreateOrEditChapterComponent) : Child()

        data class CreateOrEditScene(val component: CreateOrEditScenesComponent) : Child()
    }

}