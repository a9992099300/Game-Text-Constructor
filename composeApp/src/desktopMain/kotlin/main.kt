import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.theme.AppTheme
import com.a9992099300.gameTextConstructor.ui.navigation.RootComponent
import com.a9992099300.gameTextConstructor.ui.navigation.RootComponentImpl
import com.a9992099300.gameTextConstructor.ui.navigation.RootContent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

fun main() = application {

    Inject.initDI()
    val lifecycle = LifecycleRegistry()
    val rootComponent = root(DefaultComponentContext(lifecycle = lifecycle))


    Window(
        title = "Game Text Constructor",
        state = rememberWindowState(width = 1600.dp, height = 1600.dp),
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
