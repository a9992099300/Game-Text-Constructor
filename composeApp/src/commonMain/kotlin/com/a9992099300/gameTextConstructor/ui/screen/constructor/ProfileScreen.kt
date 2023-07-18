package com.a9992099300.gameTextConstructor.ui.screen.constructor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.data.profile.models.ProfileDataModel
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.constructor.profile.ProfileConstructorComponent
import com.a9992099300.gameTextConstructor.ui.widgets.CommonButton
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import com.a9992099300.gameTextConstructor.ui.widgets.CommonTextFieldOutline
import com.a9992099300.gameTextConstructor.ui.widgets.HeaderText

@Suppress("SuspiciousIndentation")
@Composable
fun ProfileScreen(component: ProfileConstructorComponent) {

    val name by component.name.collectAsState()
    val stateUi by component.stateUi.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp, 0.dp, 20.dp, 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopStart,
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(30.dp),
                horizontalAlignment = Alignment.Start
            ) {

                HeaderText(
                    text = MainRes.string.profile_edit,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        CommonTextFieldOutline(
                            text = name,
                            hint = MainRes.string.your_name,
                            enabled = stateUi !is StateUi.Loading,
                            onValueChanged = component::onNameChanged,
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        CommonTextFieldOutline(
                            text = if (stateUi is StateUi.Success) {
                                (stateUi as StateUi.Success<ProfileDataModel>).value.email
                            } else "",
                            hint = MainRes.string.your_login,
                            enabled = false,
                            onValueChanged = {

                            },
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                CommonButton(
                    onClickButton = component::onSaveChanged,
                    isLoading = stateUi is StateUi.Loading,
                    text = MainRes.string.save_changed
                )

            }
        }
    }

    if (stateUi is StateUi.Error) {
        CommonSnackBar((stateUi as StateUi.Error).messageError)
    }
}