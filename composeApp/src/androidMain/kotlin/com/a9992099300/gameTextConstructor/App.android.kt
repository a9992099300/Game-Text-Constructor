package com.a9992099300.gameTextConstructor

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat.startActivity
import com.a9992099300.gameTextConstructor.data.auth.models.AuthResponseBody
import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.di.Inject.instance
import com.a9992099300.gameTextConstructor.di.PlatformConfiguration
import com.a9992099300.gameTextConstructor.navigation.RootComponent
import com.a9992099300.gameTextConstructor.navigation.RootComponentImpl
import com.a9992099300.gameTextConstructor.theme.AppTheme
import com.a9992099300.gameTextConstructor.ui.screen.LoginScreen
import com.a9992099300.gameTextConstructor.ui.screen.MainScreen
import com.a9992099300.gameTextConstructor.ui.screen.RegistrationScreen
import com.a9992099300.gameTextConstructor.ui.screen.SplashScreen
import com.a9992099300.gameTextConstructor.utils.initLogger
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import io.github.aakira.napier.Napier
import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


const val log = "myLogAndroid"

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Inject.initDI(
            config = PlatformConfiguration(applicationContext)
        )
        initLogger()
        CoroutineScope(Dispatchers.Default).launch {
            val authData: AuthResponseBody? = instance<KStore<SavedAuth>>().get()?.firstOrNull()
            Napier.d(message = "test log $authData", tag = log)
        }

    }
}

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root =
            RootComponentImpl(
                componentContext = defaultComponentContext(),
            )

        setContent {
            AppTheme {
                RootContent(component = root, modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.childStack,
        modifier = modifier ,
        animation = stackAnimation(fade() + scale()),
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

internal actual fun openUrl(url: String?) {
    val uri = url?.let { Uri.parse(it) } ?: return
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = uri
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    AndroidApp.INSTANCE.startActivity(intent)
}

internal actual fun finish(platformConfiguration: PlatformConfiguration) {
    val homeIntent = Intent(Intent.ACTION_MAIN)
    homeIntent.addCategory(Intent.CATEGORY_HOME)
    homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    startActivity(platformConfiguration.context, homeIntent, null)
}
