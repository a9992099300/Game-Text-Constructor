import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.di.PlatformConfiguration
import com.a9992099300.gameTextConstructor.theme.AppTheme
import com.a9992099300.gameTextConstructor.navigation.RootComponent
import com.a9992099300.gameTextConstructor.navigation.RootComponentImpl
import com.a9992099300.gameTextConstructor.ui.navigation.RootContent
import com.a9992099300.gameTextConstructor.utils.initLogger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import io.github.aakira.napier.Napier

const val log = "myLogDesktop"

fun main() = application {

    Inject.initDI(
        config = PlatformConfiguration(
            applicationScope = this
        ))
    initLogger()
    Napier.d(message = "test log", tag = log)

    val lifecycle = LifecycleRegistry()
    val rootComponent = root(DefaultComponentContext(lifecycle = lifecycle))

    Window(
        title = "Game Text Constructor",
        state = rememberWindowState(width = 1200.dp, height = 800.dp),
        onCloseRequest = ::exitApplication,
    ) {
        //App()
        Surface(modifier = Modifier.fillMaxSize()) {
            AppTheme {
                RootContent(rootComponent)
            }
        }

    }


}

private fun root(componentContext: ComponentContext): RootComponent =
    RootComponentImpl(
        componentContext = componentContext,
    )
