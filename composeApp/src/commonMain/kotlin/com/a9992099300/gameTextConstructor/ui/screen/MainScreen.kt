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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
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


@Composable
fun MainScreen(component: MainComponent) {

    val stateUi by component.stateUi.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.secondaryBackground),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(0.dp, 450.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val painter = rememberAsyncImagePainter(
                url = DEFAULT_IMAGE_BOOK
            )
            Image(
                painter = painter,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )

            Text(
                text = "Меню",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                color = Theme.colors.primaryAction,
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

            Spacer(modifier = Modifier.height(16.dp))

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

        }
    }

    if (stateUi is StateUi.Error) {
        CommonSnackBar(
            message = (stateUi as StateUi.Error).messageError,
            component = component as? BaseComponent<Unit>
        )

    }
}