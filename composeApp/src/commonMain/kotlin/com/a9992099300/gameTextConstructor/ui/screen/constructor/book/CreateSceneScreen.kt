package com.a9992099300.gameTextConstructor.ui.screen.constructor.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.constructor.createScenes.CreateOrEditScenesComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.widgets.CommonDialog
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import com.a9992099300.gameTextConstructor.ui.widgets.CommonTextFieldOutline
import com.a9992099300.gameTextConstructor.ui.widgets.HeaderText
import compose.icons.FeatherIcons
import compose.icons.feathericons.Save


@Composable
fun CreateOrEditSceneScreen(component: CreateOrEditScenesComponent) {

    val stateUi by component.stateUi.collectAsState()

    val chapterState by component.sceneState.collectAsState()

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
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {

                EditSceneHeader(
                    component,
                    stateUi
                )

                CommonTextFieldOutline(
                    text = chapterState.sceneNumber.toString(),
                    hint = MainRes.string.number_scene,
                    onValueChanged = {
                        component.changeNumber(it)
                    },
                )

                Spacer(modifier = Modifier.height(8.dp))

                CommonTextFieldOutline(
                    text = chapterState.title,
                    hint = MainRes.string.title_scene,
                    onValueChanged = {
                        component.changeTitle(it)
                    },
                )

                Spacer(modifier = Modifier.height(8.dp))

                CommonTextFieldOutline(
                    text = chapterState.description,
                    height = 256,
                    hint = MainRes.string.description_scene,
                    onValueChanged = {
                        component.changeDescription(it)
                    },
                )



                Spacer(modifier = Modifier.weight(1f))


            }
        }
    }

    if (stateUi is StateUi.Error) {
        CommonSnackBar(
            message = (stateUi as StateUi.Error).messageError,
            component = component as? BaseComponent<Unit>
        )
    }
}

@Composable
private fun EditSceneHeader(
    component: CreateOrEditScenesComponent,
    stateUi: StateUi<Unit>
) {

    var isDeleteRequested by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        HeaderText(
            modifier = Modifier.padding(16.dp),
            text = if (component.editeSceneModel.isNotBlank())
                MainRes.string.edit_scene else MainRes.string.add_scene
        )

        Spacer(modifier = Modifier.weight(1f))

        if (stateUi is StateUi.Loading) {
            CircularProgressIndicator(
                modifier = Modifier,
                color = Theme.colors.primaryAction
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Icon(
            modifier = Modifier
                .padding(16.dp)
                .size(24.dp)
                .clickable {
                    component.onBackClicked()
                },
            imageVector = Icons.Default.ArrowBack,
            contentDescription = Icons.Default.ArrowBack.name,
            tint = Theme.colors.primaryAction,
        )

        Icon(
            modifier = Modifier
                .padding(16.dp)
                .size(24.dp)
                .clickable {
                    component.addOrEditScene()
                },
            imageVector = FeatherIcons.Save,
            contentDescription = FeatherIcons.Save.name,
            tint = Theme.colors.primaryAction,
        )
        if (component.editeSceneModel.isNotBlank()) {
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
                    .clickable {
                        isDeleteRequested = true
                    },
                imageVector = Icons.Default.Delete,
                contentDescription = Icons.Default.Delete.name,
                tint = Theme.colors.primaryAction,
            )
        }
    }

    if (isDeleteRequested) {
        CommonDialog(
            text = MainRes.string.delete_scene,
            onCanceled = {
                isDeleteRequested = false
            },
            onSuccess = {
                isDeleteRequested = false
                component.deleteScene()
            }
        )
    }
}