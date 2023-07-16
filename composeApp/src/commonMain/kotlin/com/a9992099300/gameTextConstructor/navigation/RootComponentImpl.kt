package com.a9992099300.gameTextConstructor.navigation

import com.a9992099300.gameTextConstructor.logic.constructor.RootConstructorComponent
import com.a9992099300.gameTextConstructor.logic.constructor.RootConstructorComponentImpl
import com.a9992099300.gameTextConstructor.logic.login.LogInComponent
import com.a9992099300.gameTextConstructor.logic.login.LoginComponentImpl
import com.a9992099300.gameTextConstructor.logic.main.MainComponent
import com.a9992099300.gameTextConstructor.logic.main.MainComponentImpl
import com.a9992099300.gameTextConstructor.logic.registration.RegistrationComponent
import com.a9992099300.gameTextConstructor.logic.registration.RegistrationComponentImpl
import com.a9992099300.gameTextConstructor.logic.splash.SplashComponent
import com.a9992099300.gameTextConstructor.logic.splash.SplashComponentImpl
import com.a9992099300.gameTextConstructor.utils.Consumer
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.badoo.reaktive.base.Consumer

class RootComponentImpl constructor(
    componentContext: ComponentContext,
    private val login: (ComponentContext, Consumer<LogInComponent.Login>) -> LogInComponent,
    private val main: (ComponentContext, Consumer<MainComponent.Main>) -> MainComponent,
    private val splash: (ComponentContext, Consumer<SplashComponent.Splash>) -> SplashComponent,
    private val constructor: (ComponentContext, Consumer<MainComponent.Main>) -> RootConstructorComponent,
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
                openMain = {
                    output.onNext(LogInComponent.Login.Main)
                },
                openRootConstructor = {
                    output.onNext(LogInComponent.Login.RootConstructor)
                }
            )
        },
        main = { childContext, output ->
            MainComponentImpl(
                componentContext = childContext
            )
        },
        splash = { childContext, output ->
            SplashComponentImpl(
                componentContext = childContext,
                openMain = {
                    output.onNext(SplashComponent.Splash.Main)
                },
                openLogin = {
                    output.onNext(SplashComponent.Splash.Login)
                }
            )
        },
        constructor = { childContext, output ->
            RootConstructorComponentImpl(
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
            initialConfiguration = Configuration.Splash,
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
            is Configuration.Splash -> RootComponent.Child.Splash(
                splash(
                    componentContext, Consumer(::splashFinished)
                )
            )
            is Configuration.Main -> RootComponent.Child.Main(
                main(componentContext, Consumer(::exit))
            )

            is Configuration.RootConstructor -> RootComponent.Child.RootConstructor(
                constructor(componentContext, Consumer(::exit))
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
            is LogInComponent.Login.Main -> navigation.push(Configuration.Main)
            is LogInComponent.Login.RootConstructor -> navigation.replaceCurrent(Configuration.RootConstructor)
            is LogInComponent.Login.Registration -> navigation.push(Configuration.Registration)
        }

    private fun splashFinished(output: SplashComponent.Splash): Unit =
        when (output) {
            is SplashComponent.Splash.Login -> navigation.replaceCurrent(Configuration.Login)
            is SplashComponent.Splash.Main -> navigation.replaceCurrent(Configuration.RootConstructor)
        }

    private fun <T> exit(output: T): Unit =
        navigation.pop()

    sealed class Configuration : Parcelable {
        @Parcelize
        object Login : Configuration()

        @Parcelize
        object Main : Configuration()

        @Parcelize
        object Splash : Configuration()

        @Parcelize
        object RootConstructor : Configuration()

        @Parcelize
        object Registration : Configuration()
    }

}

