package com.a9992099300.gameTextConstructor.ui.screen.constructor.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.constructor.createPage.CreateOrEditPageComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.widgets.CommonCard
import com.a9992099300.gameTextConstructor.ui.widgets.CommonDialog
import com.a9992099300.gameTextConstructor.ui.widgets.CommonPlusCard
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import com.a9992099300.gameTextConstructor.ui.widgets.CommonText
import com.a9992099300.gameTextConstructor.ui.widgets.CommonTextFieldOutline
import com.a9992099300.gameTextConstructor.ui.widgets.HeaderText
import compose.icons.FeatherIcons
import compose.icons.feathericons.Save


@Composable
fun CreatePageScreen(component: CreateOrEditPageComponent) {

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
            PageHeader(component)
            CreatePageContent(component)
        }
    }

    if (stateUi is StateUi.Error) {
        CommonSnackBar(
            message = (stateUi as StateUi.Error).messageError,
            component = component as? BaseComponent<Unit>,
            dismissSnack = {
                 component.resetError()
            }
        )
    }
}


@Composable
fun PageHeader(
    component: CreateOrEditPageComponent
) {

    var isDeleteRequested by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        HeaderText(
            modifier = Modifier.padding(16.dp),
            text = MainRes.string.add_page,
        )

        Spacer(modifier = Modifier.weight(1f))

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
                    component.savePage()
                },
            imageVector = FeatherIcons.Save,
            contentDescription = FeatherIcons.Save.name,
            tint = Theme.colors.primaryAction,
        )
    }

    if (isDeleteRequested) {
        CommonDialog(
            text = MainRes.string.delete_chapter,
            onCanceled = {
                isDeleteRequested = false
            },
            onSuccess = {
                isDeleteRequested = false
                // component.deleteChapter()
            }
        )
    }
}


@Composable
fun CreatePageContent(
    component: CreateOrEditPageComponent
) {

    val uiPageModel by component.uiPageModel.collectAsState()

    CommonTextFieldOutline(
        text = uiPageModel.title,
        hint = MainRes.string.title_page,
        onValueChanged = {
            component.changeTitle(it)
        },
    )

    Spacer(modifier = Modifier.height(8.dp))

    CommonTextFieldOutline(
        text = uiPageModel.description,
        hint = MainRes.string.description_page,
        height = 150,
        onValueChanged = {
            component.changeDescription(it)
        },
    )

    CommonText(
        modifier = Modifier.padding(16.dp),
        text = MainRes.string.actions,
        size = 24
    )


    Actions(component)

}

@Composable
fun Actions(component: CreateOrEditPageComponent) {

    val uiAction by component.uiActions.collectAsState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(
            items = uiAction,
            key = { it.id }
        ) { itemPage ->
            CommonCard(
                modifier = Modifier
                    .width(220.dp)
                    .height(150.dp)
                    .clickable {
                        // component.loadPages(scene.sceneId)
                    },
                description = itemPage.description,
                onEdit = {
                    // component.onEditScene(itemPage)
                }
            )
        }

        item {
            CommonPlusCard(
                modifier = Modifier
                    .width(220.dp)
                    .height(150.dp)
                    .clickable {
                        component.onCreateAction()
                    },
                text = MainRes.string.add_action
            )

        }
    }
}