package com.a9992099300.gameTextConstructor

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.di.PlatformConfiguration
import com.a9992099300.gameTextConstructor.theme.AppTheme
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.widgets.CommonButton
import com.a9992099300.gameTextConstructor.ui.widgets.CommonTextField

@Composable
internal fun App() = AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.primaryBackground),
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
                        .fillMaxWidth()

                        .padding(40.dp, 24.dp, 40.dp, 24.dp),
                    color = Theme.colors.primaryAction,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                CommonTextField(
                    text = "login",
                    hint = MainRes.string.your_login,
                    enabled = true,
                    onValueChanged = {

                    },
                )

                Spacer(modifier = Modifier.height(12.dp))

                CommonTextField(
                    text = "password",
                    hint = MainRes.string.your_password,
                    enabled = true,
                    onValueChanged = {

                                     },
                )

                Spacer(modifier = Modifier.height(24.dp))

                CommonButton(
                    onClickButton = {  },
                    isLoading = false,
                    text = MainRes.string.enter
                )

                Spacer(modifier = Modifier.height(24.dp))

                CommonButton(
                    onClickButton = {

                    },
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

    }

internal expect fun openUrl(url: String?)

internal expect fun finish(platformConfiguration: PlatformConfiguration)
