package com.a9992099300.gameTextConstructor.navigation

import com.a9992099300.gameTextConstructor.logic.auth.SignInComponent
import com.a9992099300.gameTextConstructor.logic.main.MainComponent
import com.a9992099300.gameTextConstructor.logic.registration.RegistrationComponent
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class SignIn(val component: SignInComponent) : Child()
        data class Main(val component: MainComponent) : Child()
        data class Registration(val component: RegistrationComponent) : Child()
    }
}