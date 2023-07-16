package com.a9992099300.gameTextConstructor.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.splash.SplashComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import io.github.skeptick.libres.compose.painterResource


@Composable
fun SplashScreen(component: SplashComponent) {

    val name by component.name.collectAsState()
    val animated by remember { mutableStateOf(false) }
    val rotation = remember { Animatable(initialValue = 360f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.secondaryBackground),
        contentAlignment = Alignment.Center,
    ) {
        LaunchedEffect(animated) { //анимация не работает
            rotation.animateTo(
                targetValue = if (animated) 0f else 360f,
                animationSpec = tween(durationMillis = 1000),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.graphicsLayer {
                    rotationY = rotation.value
                }
                    .size(60.dp),
                painter = painterResource(MainRes.image.ic_dice),
                contentDescription = "",
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (name.isNotBlank()) {
                    MainRes.string.string_with_arguments.format(name = name)
                } else name,
                modifier = Modifier
                    .fillMaxWidth(),
                color = Theme.colors.primaryAction,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 24.sp,
                    shadow = Shadow(
                        color = Theme.colors.primaryBackground,
                        offset = Offset(0f, 2f),
                        blurRadius = 3f
                    )
                ),
            )
        }

    }
}