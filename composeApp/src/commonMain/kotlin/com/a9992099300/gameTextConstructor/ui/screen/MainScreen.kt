package com.a9992099300.gameTextConstructor.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.main.MainComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.widgets.CommonButton
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import com.a9992099300.gameTextConstructor.utils.DEFAULT_IMAGE_BOOK
import com.seiko.imageloader.rememberAsyncImagePainter

val largeRadialGradient = object : ShaderBrush() {
    override fun createShader(size: Size): Shader {
        val biggerDimension = maxOf(size.height, size.width)
        return RadialGradientShader(
            colors = listOf(Color(0xFF26252d), Color(0xFFe6e0ec)),
            center = size.center,
            radius = biggerDimension / 2f,
            colorStops = listOf(0f, 1.0f)
        )
    }
}

val listColors = listOf(Color(0xFFe6e0ec), Color(0xFFe6e0ec))

val customBrush =
    object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            return LinearGradientShader(
                colors = listColors,
                from = Offset.Zero,
                to = Offset(size.width / 2f, 0f),
                tileMode = TileMode.Mirror
            )
        }
    }

val colorStops = arrayOf(
    0.0f to Color(0xFF070b10),
    1f to Color(0xFFe6e0ec)
)

val colorStops2 = arrayOf(
    0.0f to Color(0x00ffffff),
    1f to Color(0xFF070b10)
)

@Composable
fun MainScreen(component: MainComponent) {

    val stateUi by component.stateUi.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF070b10)),
    ) {
        val painter = rememberAsyncImagePainter(
            url = DEFAULT_IMAGE_BOOK
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(){
                Image(
                    painter = painter,
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
                Box(modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(colorStops = colorStops2))
                )
            }

        }


        Column(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(0.dp, 450.dp)
                .background(Brush.verticalGradient(colorStops = colorStops)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Меню",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 0.dp),
                color = Theme.colors.secondaryTextColor,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 28.sp,
                    shadow = Shadow(
                        color = Theme.colors.primaryBackground,
                        offset = Offset(0f, 2f),
                        blurRadius = 3f
                    )
                ),
            )

            Spacer(modifier = Modifier.height(24.dp))

            CommonButton(
                onClickButton = {

                },
                isLoading = stateUi is StateUi.Loading,
                text = MainRes.string.list_books,
                modifier = Modifier.padding(30.dp, 0.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            CommonButton(
                onClickButton = {

                },
                isLoading = false,
                text = MainRes.string.profile_edit,
                modifier = Modifier.padding(30.dp, 0.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            CommonButton(
                onClickButton = {

                },
                isLoading = false,
                text = MainRes.string.exit,
                modifier = Modifier.padding(30.dp, 0.dp)
            )

            Spacer(modifier = Modifier.height(120.dp))

        }
    }

    if (stateUi is StateUi.Error) {
        CommonSnackBar(
            message = (stateUi as StateUi.Error).messageError,
            component = component as? BaseComponent<Unit>
        )

    }
}