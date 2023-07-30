package com.a9992099300.gameTextConstructor.ui.screen.constructor

import androidx.compose.runtime.Composable
import com.a9992099300.gameTextConstructor.logic.constructor.rootBook.RootBookConstructorComponent
import com.a9992099300.gameTextConstructor.ui.screen.constructor.book.BookScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation

@Composable
fun RootBookContent(component: RootBookConstructorComponent) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is RootBookConstructorComponent.Child.Book -> {
                BookScreen(child.component)
            }
        }
    }
}