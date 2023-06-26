@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.a9992099300.gameTextConstructor.ui.navigation

import androidx.compose.runtime.Composable
import com.a9992099300.gameTextConstructor.ui.screen.MainScreen
import com.a9992099300.gameTextConstructor.ui.screen.SignScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation

@Composable
fun RootContent(component: RootComponent) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.SignIn -> SignScreen(child.component)
            is RootComponent.Child.Main -> MainScreen(child.component)
        }
    }
}
