package com.a9992099300.gameTextConstructor.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.login.LogInComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.widgets.CommonButton
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import com.a9992099300.gameTextConstructor.ui.widgets.CommonTextFieldOutline
import compose.icons.FeatherIcons
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff

@Composable
fun LoginScreen(component: LogInComponent) {

    val login by component.login.collectAsState()
    val password by component.password.collectAsState()
    val stateUi by component.stateUi.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.secondaryBackground),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(30.dp)
                .widthIn(0.dp, 450.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = MainRes.string.enter_title,
                modifier = Modifier
                    .fillMaxWidth(),
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

            CommonTextFieldOutline(
                text = login,
                hint = MainRes.string.your_login,
                enabled = stateUi !is StateUi.Loading,
                onValueChanged = component::onLoginChanged,
            )

            Spacer(modifier = Modifier.height(12.dp))

            CommonTextFieldOutline(
                text = password.first,
                hint = MainRes.string.your_password,
                enabled = stateUi !is StateUi.Loading,
                onValueChanged = component::onPasswordChanged,
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            component.onVisibleChanged(!password.second)
                        },
                        imageVector = if (password.second) {
                            FeatherIcons.EyeOff
                        } else {
                            FeatherIcons.Eye
                        }, contentDescription = "Password hidden",
                        tint = Theme.colors.hintTextColor
                    )
                },
                isSecure = password.second
            )

            Spacer(modifier = Modifier.height(24.dp))

            CommonButton(
                onClickButton = component::onSignInClick,
                isLoading = stateUi is StateUi.Loading,
                text = MainRes.string.enter
            )

            Spacer(modifier = Modifier.height(24.dp))

            CommonButton(
                onClickButton = component::onRegistrationClick,
                isLoading = false,
                text = MainRes.string.registry
            )

            Text(
                text = MainRes.string.forgetting_password,
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(40.dp, 24.dp, 40.dp, 24.dp),
                color = Theme.colors.primaryAction,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
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



