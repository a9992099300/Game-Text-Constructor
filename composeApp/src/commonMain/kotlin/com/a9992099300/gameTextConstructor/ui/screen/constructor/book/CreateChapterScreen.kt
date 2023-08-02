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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.constructor.createChapter.CreateChapterComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.widgets.CommonButton
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import com.a9992099300.gameTextConstructor.ui.widgets.CommonTextFieldOutline
import com.a9992099300.gameTextConstructor.ui.widgets.HeaderText
import compose.icons.FeatherIcons
import compose.icons.feathericons.Save


@Composable
fun CreateChapterScreen(component: CreateChapterComponent) {

    val stateUi by component.stateUi.collectAsState()

    val chapterState by component.chapterState.collectAsState()

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

                if (component.editedChapterModel != null) {
                    EditChapterHeader(
                        component
                    )
                } else {
                    HeaderText(
                        text = MainRes.string.new_chapter,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                CommonTextFieldOutline(
                    text = chapterState.chapterNumber.toString(),
                    hint = MainRes.string.number_chapter,
                    onValueChanged = {
                        component.changeNumber(it)
                    },
                )

                CommonTextFieldOutline(
                    text = chapterState.title,
                    hint = MainRes.string.title_chapter,
                    onValueChanged = {
                        component.changeTitle(it)
                    },
                )

                CommonTextFieldOutline(
                    text = chapterState.description,
                    height = 256,
                    hint = MainRes.string.description_chapter,
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
                            component.addChapter()
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
        CommonSnackBar((stateUi as StateUi.Error).messageError)
    }
}

@Composable
private fun EditChapterHeader(
    component: CreateChapterComponent,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp, 0.dp, 16.dp, 0.dp)
    ) {

        HeaderText(
            text = MainRes.string.edit_chapter,
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
                  //  component.c
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
                    component.deleteChapter()
                },
            imageVector = Icons.Default.Delete,
            contentDescription = Icons.Default.Delete.name,
            tint = Theme.colors.primaryAction,
        )
    }
}