import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.di.PlatformConfiguration
import com.a9992099300.gameTextConstructor.theme.AppTheme
import com.a9992099300.gameTextConstructor.logic.root.RootComponent
import com.a9992099300.gameTextConstructor.logic.root.RootComponentImpl
import com.a9992099300.gameTextConstructor.ui.root.RootContent
import com.a9992099300.gameTextConstructor.utils.ApplicationName
import com.a9992099300.gameTextConstructor.utils.initLogger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import okio.Path.Companion.toOkioPath
import java.io.File

const val log = "myLogDesktop"

fun main() = application {

    Inject.initDI(
        config = PlatformConfiguration(
            applicationScope = this
        ))
    initLogger()

    val lifecycle = LifecycleRegistry()
    val rootComponent = root(DefaultComponentContext(lifecycle = lifecycle))

    Window(
        title = "Game Text Constructor",
        state = rememberWindowState(width = 1200.dp, height = 800.dp),
        onCloseRequest = ::exitApplication,
    ) {
        CompositionLocalProvider(
            LocalImageLoader provides remember { generateImageLoader() },
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                AppTheme {
                    RootContent(rootComponent)
                }
            }
        }
    }


}

private fun generateImageLoader(): ImageLoader {
    return ImageLoader {
        components {
            setupDefaultComponents()
        }
        interceptor {
            memoryCacheConfig {
                // Set the max size to 25% of the app's available memory.
                maxSizePercent(0.25)
            }
            diskCacheConfig {
                directory(getCacheDir().toOkioPath().resolve("image_cache"))
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}

enum class OperatingSystem {
    Windows, Linux, MacOS, Unknown
}

private val currentOperatingSystem: OperatingSystem
    get() {
        val operSys = System.getProperty("os.name").lowercase()
        return if (operSys.contains("win")) {
            OperatingSystem.Windows
        } else if (operSys.contains("nix") || operSys.contains("nux") ||
            operSys.contains("aix")
        ) {
            OperatingSystem.Linux
        } else if (operSys.contains("mac")) {
            OperatingSystem.MacOS
        } else {
            OperatingSystem.Unknown
        }
    }

private fun getCacheDir() = when (currentOperatingSystem) {
    OperatingSystem.Windows -> File(System.getenv("AppData"), "$ApplicationName/cache")
    OperatingSystem.Linux -> File(System.getProperty("user.home"), ".cache/$ApplicationName")
    OperatingSystem.MacOS -> File(System.getProperty("user.home"), "Library/Caches/$ApplicationName")
    else -> throw IllegalStateException("Unsupported operating system")
}

private fun root(componentContext: ComponentContext): RootComponent =
    RootComponentImpl(
        componentContext = componentContext,
    )
