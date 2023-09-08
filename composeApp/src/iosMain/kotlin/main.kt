import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.di.PlatformConfiguration
import com.a9992099300.gameTextConstructor.logic.root.RootComponent
import com.a9992099300.gameTextConstructor.logic.root.RootComponentImpl
import com.a9992099300.gameTextConstructor.theme.AppTheme
import com.a9992099300.gameTextConstructor.ui.screen.MainScreen
import com.a9992099300.gameTextConstructor.ui.screen.RegistrationScreen
import com.a9992099300.gameTextConstructor.ui.screen.LoginScreen
import com.a9992099300.gameTextConstructor.ui.screen.SplashScreen
import com.a9992099300.gameTextConstructor.utils.initLogger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {

    Inject.initDI(
        config = PlatformConfiguration()
    )
    initLogger()
    val lifecycle = LifecycleRegistry()
    val rootComponent = root(DefaultComponentContext(lifecycle = lifecycle))

    return ComposeUIViewController {
        CompositionLocalProvider(
            LocalImageLoader provides remember { generateImageLoader() },
        ) {
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
            is RootComponent.Child.Splash -> SplashScreen(child.component)
            is RootComponent.Child.RootConstructor -> {
                // переход на сайт: создать свою книгу
            }
        }

    }
}

private fun generateImageLoader(): ImageLoader {
    return ImageLoader {
       // commonConfig()
        components {
            setupDefaultComponents()
        }
        interceptor {
            memoryCacheConfig {
                // Set the max size to 25% of the app's available memory.
                maxSizePercent(0.25)
            }
            diskCacheConfig {
                directory(getCacheDir().toPath().resolve("image_cache"))
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun getCacheDir(): String {
    return NSFileManager.defaultManager.URLForDirectory(
        NSCachesDirectory,
        NSUserDomainMask,
        null,
        true,
        null,
    )!!.path.orEmpty()
}


