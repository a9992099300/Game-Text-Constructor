package com.a9992099300.gameTextConstructor.ui.screen.constructor

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
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.constructor.createBook.CreateBookConstructorComponent
import com.a9992099300.gameTextConstructor.ui.widgets.CommonButton
import com.a9992099300.gameTextConstructor.ui.widgets.CommonFilterChip
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import com.a9992099300.gameTextConstructor.ui.widgets.CommonTextFieldOutline
import com.a9992099300.gameTextConstructor.ui.widgets.HeaderText
import com.a9992099300.gameTextConstructor.utils.TypeCategory

@Suppress("SuspiciousIndentation")
@Composable
fun CreateBookScreen(component: CreateBookConstructorComponent) {

    val stateUi by component.stateUi.collectAsState()

    val titleBook by component.titleBook.collectAsState()

    val descriptionBook by component.descriptionBook.collectAsState()

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
                    text = MainRes.string.new_book,
                )

                Spacer(modifier = Modifier.height(16.dp))

                CommonTextFieldOutline(
                    text = titleBook,
                    hint = MainRes.string.title_book,
                    onValueChanged = {
                        component.changeTitle(it)
                    },
                )

                ChooseCategory(
                    component,
                    onClickCategory = {
                        component.chooseCategory(it)
                    })

                CommonTextFieldOutline(
                    text = descriptionBook,
                    height = 256,
                    hint = MainRes.string.description_book,
                    onValueChanged = {
                        component.changeDescription(it)
                    },
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CommonButton(
                        onClickButton = {
                            component.onBackClicked()
                        },
                        text = MainRes.string.cancel,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    CommonButton(
                        onClickButton = {
                            component.addBook()
                        },
                        isLoading = stateUi is StateUi.Loading,
                        text = MainRes.string.save,
                        modifier = Modifier.weight(1f)
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
fun ChooseCategory(component: CreateBookConstructorComponent, onClickCategory:(TypeCategory) -> Unit) {
    val stateCategory by component.stateCategory.collectAsState()

    Column {
        FlowRow(
            Modifier
                .fillMaxWidth(1f)
                .wrapContentHeight(align = Alignment.Top),
            horizontalArrangement = Arrangement.Start,
        ) {
            stateCategory.forEach {
                CommonFilterChip(it,onClickCategory)
            }
        }
    }
}