package com.a9992099300.gameTextConstructor.navigation

import com.a9992099300.gameTextConstructor.logic.login.LogInComponent
import com.a9992099300.gameTextConstructor.logic.login.LoginComponentImpl
import com.a9992099300.gameTextConstructor.logic.main.MainComponent
import com.a9992099300.gameTextConstructor.logic.main.MainComponentImpl
import com.a9992099300.gameTextConstructor.logic.registration.RegistrationComponent
import com.a9992099300.gameTextConstructor.logic.registration.RegistrationComponentImpl
import com.a9992099300.gameTextConstructor.utils.Consumer

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.badoo.reaktive.base.Consumer

class RootComponentImpl constructor(
    componentContext: ComponentContext,
    private val login: (ComponentContext, Consumer<LogInComponent.Login>) -> LogInComponent,
    private val main: (ComponentContext, Consumer<MainComponent.Main>) -> MainComponent,
    private val registration: (ComponentContext, Consumer<RegistrationComponent.Registration>) -> RegistrationComponent,
) : RootComponent, ComponentContext by componentContext {
    constructor(
        componentContext: ComponentContext,
    ) : this(
        componentContext = componentContext,
        login = { childContext, output ->
            LoginComponentImpl(
                componentContext = childContext,
                registrationClicked = {
                    output.onNext(LogInComponent.Login.Registration)
                },
            )
        },
        main = { childContext, output ->
            MainComponentImpl(
                componentContext = childContext
            )
        },
        registration = { childContext, output ->
            RegistrationComponentImpl(
                componentContext = childContext,
                backClicked = {
                    output.onNext(RegistrationComponent.Registration.Back)
                }
            )
        }
    )

    private val navigation = StackNavigation<Configuration>()

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Configuration.Login,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = stack

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): RootComponent.Child =
        when (configuration) {
            is Configuration.Login -> RootComponent.Child.Login(
                login(
                    componentContext, Consumer(::loginSuccess)
                )
            )

            is Configuration.Main -> RootComponent.Child.Main(
                main(componentContext, Consumer(::exit))
            )

            is Configuration.Registration -> RootComponent.Child.Registration(
                registration(
                    componentContext,
                    Consumer(::exit)
                )
            )
        }

    private fun loginSuccess(output: LogInComponent.Login): Unit =
        when (output) {
            is LogInComponent.Login.Finished -> navigation.push(Configuration.Login)
            is LogInComponent.Login.Registration -> navigation.push(Configuration.Registration)
        }


    private fun <T> exit(output: T): Unit =
        navigation.pop()

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Login : Configuration()

        @Parcelize
        object Main : Configuration()

        @Parcelize
        object Registration : Configuration()
    }

}

