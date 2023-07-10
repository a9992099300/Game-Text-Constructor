@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.a9992099300.gameTextConstructor.ui.navigation

import androidx.compose.runtime.Composable
import com.a9992099300.gameTextConstructor.navigation.RootComponent
import com.a9992099300.gameTextConstructor.ui.screen.MainScreen
import com.a9992099300.gameTextConstructor.ui.screen.RegistrationScreen
import com.a9992099300.gameTextConstructor.ui.screen.LoginScreen
import com.a9992099300.gameTextConstructor.ui.screen.constructor.MainConstructorScreen
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
            is RootComponent.Child.Login -> LoginScreen(child.component)
            is RootComponent.Child.Main -> MainScreen(child.component)
            is RootComponent.Child.Registration -> RegistrationScreen(child.component)
            is RootComponent.Child.RootConstructor -> MainConstructorScreen(child.component)
        }
    }
}
