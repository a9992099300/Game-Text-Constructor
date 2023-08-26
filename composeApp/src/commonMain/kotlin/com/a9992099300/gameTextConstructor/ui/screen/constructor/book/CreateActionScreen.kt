package com.a9992099300.gameTextConstructor.ui.screen.constructor.book

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.constructor.action.CreateOrEditActionComponent
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar

@Composable
fun CreateActionScreen(component: CreateOrEditActionComponent) {

    val stateUi by component.stateUi.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp, 0.dp, 8.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {

        }

        if (stateUi is StateUi.Error) {
            CommonSnackBar(
                message = (stateUi as StateUi.Error).messageError,
                component = component as? BaseComponent<Unit>,
                dismissSnack = {
                    // component.resetError()
                }
            )
        }
    }
}