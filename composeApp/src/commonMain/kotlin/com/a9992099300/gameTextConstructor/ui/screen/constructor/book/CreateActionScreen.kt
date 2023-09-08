package com.a9992099300.gameTextConstructor.ui.screen.constructor.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.a9992099300.gameTextConstructor.logic.constructor.action.CreateOrEditActionComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.screen.models.ActionTypeUI
import com.a9992099300.gameTextConstructor.ui.screen.models.TypeInventory
import com.a9992099300.gameTextConstructor.ui.widgets.CommonDialog
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import com.a9992099300.gameTextConstructor.ui.widgets.CommonText
import com.a9992099300.gameTextConstructor.ui.widgets.CommonTextClickable
import com.a9992099300.gameTextConstructor.ui.widgets.CommonTextFieldOutline
import com.a9992099300.gameTextConstructor.ui.widgets.HeaderText
import compose.icons.FeatherIcons
import compose.icons.feathericons.Save

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

            HeadAction(component)
            CreateActionContent(component)
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

@Composable
fun HeadAction(
    component: CreateOrEditActionComponent
) {

    var isDeleteRequested by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        HeaderText(
            modifier = Modifier.padding(16.dp),
            text = MainRes.string.add_action,
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
                    //  component.savePage()
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
fun CreateActionContent(
    component: CreateOrEditActionComponent
) {

    val uiItemPageModel by component.uiItemPageModel.collectAsState()

    CommonTextFieldOutline(
        text = uiItemPageModel.description,
        hint = MainRes.string.description_page,
        onValueChanged = {
            //  component.changeTitle(it)
        },
    )

    Spacer(modifier = Modifier.height(8.dp))

    ActionType(component, onClickCategory = {

    })



}

@Composable
fun ItemNavigation() {

    androidx.compose.material.Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ItemChoose(MainRes.string.chapter, "aasa1211221", "Выберите главу") {

                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ItemChoose(MainRes.string.scene, "aasa1211221", "Выберите сцены") {

                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ItemChoose(MainRes.string.page, "aasa1211221", "Выберите страницу") {

                }
            }

        }
    }
}


@Composable
fun ItemChoose(title: String, id: String, value: String, onClick: () -> Unit) {
    CommonText(title)
    Divider(modifier = Modifier.padding(0.dp, 4.dp))
    CommonTextClickable(
        value,
        modifier = Modifier
            .defaultMinSize(150.dp)
    ) {
        onClick()
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ActionType(component: CreateOrEditActionComponent, onClickCategory: (TypeInventory) -> Unit) {
    val stateType by component.actionType.collectAsState()

    Column {
        FlowRow(
            Modifier
                .fillMaxWidth(1f)
                .wrapContentHeight(align = Alignment.Top),
            horizontalArrangement = Arrangement.Start,
        ) {
            stateType.forEach {
                ActionFilterChip(it, onClickCategory)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionFilterChip(actionTypeUI: ActionTypeUI, onClick: (TypeInventory) -> Unit) {
    FilterChip(
        modifier = Modifier.padding(4.dp),
        selected = actionTypeUI.selected,
        onClick = {
            // onClick.invoke(categoryUiModel.typeInventory)
        },
        label = { Text(actionTypeUI.title) },
        colors = FilterChipDefaults.filterChipColors(
            selectedLabelColor = Theme.colors.secondaryTextColor,
            selectedContainerColor = Theme.colors.primaryAction
        ),
        leadingIcon = if (actionTypeUI.selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                    tint = Theme.colors.secondaryTextColor
                )
            }
        } else {
            null
        }
    )
}