import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.di.PlatformConfiguration
import com.a9992099300.gameTextConstructor.logic.root.RootComponent
import com.a9992099300.gameTextConstructor.logic.registration.RootComponentImpl
import com.a9992099300.gameTextConstructor.theme.AppTheme
import com.a9992099300.gameTextConstructor.ui.screen.MainScreen
import com.a9992099300.gameTextConstructor.ui.screen.RegistrationScreen
import com.a9992099300.gameTextConstructor.ui.screen.LoginScreen
import com.a9992099300.gameTextConstructor.utils.initLogger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.essenty.lifecycle.LifecycleRegistry


import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {

    Inject.initDI(
        config = PlatformConfiguration()
    )
    initLogger()
    val lifecycle = LifecycleRegistry()
    val rootComponent = root(DefaultComponentContext(lifecycle = lifecycle))

    return ComposeUIViewController {
        AppTheme {
            RootContent(rootComponent)
        }
    }
}

private fun root(componentContext: ComponentContext): RootComponent =
    RootComponentImpl(
        componentContext = componentContext,
    )


@Composable
fun RootContent(component: RootComponent) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Login -> LoginScreen(child.component)
            is RootComponent.Child.Main -> MainScreen(child.component)
            is RootComponent.Child.Registration -> RegistrationScreen(child.component)
            else -> {}
        }

    }
}


