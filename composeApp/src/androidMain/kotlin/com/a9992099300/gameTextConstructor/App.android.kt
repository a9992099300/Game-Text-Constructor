package com.a9992099300.gameTextConstructor

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.auth.SignInComponentImpl
import com.a9992099300.gameTextConstructor.theme.AppTheme
import com.a9992099300.gameTextConstructor.ui.screen.SignScreen
import com.arkivanov.decompose.defaultComponentContext

class AndroidApp : Application(){
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Inject.initDI()
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponent = SignInComponentImpl(defaultComponentContext())
        setContent {
            AppTheme {
                SignScreen(rootComponent)
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