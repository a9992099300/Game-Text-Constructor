package com.a9992099300.gameTextConstructor.ui.screen.constructor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.common.StateUi.Companion.ERROR_DESCRIPTION
import com.a9992099300.gameTextConstructor.logic.common.StateUi.Companion.ERROR_TITLE
import com.a9992099300.gameTextConstructor.logic.constructor.editBook.EditBookConstructorComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.widgets.CommonButton
import com.a9992099300.gameTextConstructor.ui.widgets.CommonDialog
import com.a9992099300.gameTextConstructor.ui.widgets.CommonFilterChip
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import com.a9992099300.gameTextConstructor.ui.widgets.CommonTextFieldOutline
import com.a9992099300.gameTextConstructor.ui.widgets.HeaderText
import com.a9992099300.gameTextConstructor.utils.TypeCategory
import compose.icons.FeatherIcons
import compose.icons.feathericons.Save

@Suppress("SuspiciousIndentation")
@Composable
fun EditBookScreen(component: EditBookConstructorComponent) {

    val stateUi by component.stateUi.collectAsState()
    val model by component.uiModel.collectAsState()

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
                EditBookHeader(
                    component,
                    stateUi
                )

                Spacer(modifier = Modifier.height(16.dp))

                CommonTextFieldOutline(
                    text = model.title,
                    hint = MainRes.string.title_book,
                    onValueChanged = {
                        component.changeTitle(it)
                    },
                    isError = (stateUi as? StateUi.Error)?.codeError == ERROR_TITLE
                )

                ChooseCategory(
                    component,
                    onClickCategory = {
                        component.chooseCategory(it)
                    }
                )

                CommonTextFieldOutline(
                    text = model.description,
                    height = 256,
                    hint = MainRes.string.description_book,
                    onValueChanged = {
                        component.changeDescription(it)
                    },
                    isError = (stateUi as? StateUi.Error)?.codeError == ERROR_DESCRIPTION
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CommonButton(
                        onClickButton = {
                            component.onEditScenes()
                        },
                        text = MainRes.string.scenes_edit,
                        modifier = Modifier.weight(1f),
                        isLoading = stateUi is StateUi.Loading
                    )
                }
            }
        }
    }

    if (stateUi is StateUi.Error) {
        CommonSnackBar(
            message = (stateUi as StateUi.Error).messageError,
            component = component
        )


    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChooseCategory(component: EditBookConstructorComponent, onClickCategory:(TypeCategory) -> Unit) {
    val stateCategory by component.stateCategory.collectAsState()

    Column {
        FlowRow(
            Modifier
                .fillMaxWidth(1f)
                .wrapContentHeight(align = Alignment.Top),
            horizontalArrangement = Arrangement.Start,
        ) {
            stateCategory.forEach {
                CommonFilterChip(it, onClickCategory)
            }
        }
    }
}

@Composable
private fun EditBookHeader(
    component: EditBookConstructorComponent,
    stateUi: StateUi<Unit>,
) {

    var isDeleteRequested by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp, 0.dp, 16.dp, 0.dp)
    ) {
        HeaderText(
            text = MainRes.string.edit_book,
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            modifier = Modifier
                .padding(16.dp, 0.dp)
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
                .padding(16.dp, 0.dp)
                .size(24.dp)
                .clickable {
                component.editBook()
            },
            imageVector = FeatherIcons.Save,
            contentDescription = FeatherIcons.Save.name,
            tint = Theme.colors.primaryAction,
        )

        Icon(
            modifier = Modifier
                .padding(16.dp, 0.dp)
                .size(24.dp)
                .clickable {
                    isDeleteRequested = true
            },
            imageVector = Icons.Default.Delete,
            contentDescription = Icons.Default.Delete.name,
            tint = Theme.colors.primaryAction,
        )
    }

    if (isDeleteRequested) {
        CommonDialog(
            text = MainRes.string.delete_book,
            onCanceled = {
                isDeleteRequested = false
            },
            onSuccess = {
                isDeleteRequested = false
                component.deleteBook()
            }
        )
    }
}