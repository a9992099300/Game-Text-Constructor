package com.a9992099300.gameTextConstructor.ui.screen.constructor.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.widgets.CommonCard
import com.a9992099300.gameTextConstructor.ui.widgets.CommonPlusCard
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.ArrowRight


@Composable
fun ContentChaptersList(
    component: BookConstructorComponent,
    chapterHide: Boolean
) {
    val chapters by component.chapters.collectAsState()

    HeaderChapter(
        chapterHide,
        component
    )

    //  if (!chapterHide) {
    when (val currentState = chapters) {
        is StateUi.Success -> {
            LazyColumn(
                modifier = Modifier.padding(4.dp),
                contentPadding = PaddingValues(
                    top = 8.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = currentState.value,
                    key = { it.chapterId }
                ) { chapter ->
                    if (!chapterHide) {
                        CommonCard(
                            modifier = Modifier
                                .height(160.dp)
                                .clickable {
                                    component.loadScenes(chapter.chapterId)
                                },
                            title = chapter.title,
                            description = chapter.description,
                            number = "Глава ${chapter.chapterNumber}",
                            imageUrl = chapter.imageUrl,
                            selected = chapter.selected,
                            onEdit  ={
                                component.onEditChapter(chapter)
                            }
                        )
                    } else {
                        CommonCard(
                            modifier = Modifier
                                .height(160.dp)
                                .clickable {
                                    component.loadScenes(chapter.chapterId)
                                },
                            title = chapter.title,
                            selected = chapter.selected,
                            sceneHide = true,
                            onEdit  ={
                                component.onEditChapter(chapter)
                            }
                        )
                    }
                }
                item {
                    if (currentState !is StateUi.Error && !chapterHide) {
                            CommonPlusCard(
                                modifier = Modifier
                                    .height(160.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        component.onCreateChapter()
                                    },
                                text = MainRes.string.add_chapter
                            )
                        }
                }
            }
        }

        is StateUi.Loading -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier,
                    color = Theme.colors.primaryAction
                )
            }
        }

        is StateUi.Error -> {
            CommonSnackBar(
                message = currentState.messageError,
                dismissSnack = {
                    component.loadChapters()
                }
            )
        }

        is StateUi.Initial -> {

        }
    }
    //  }
}


@Composable
fun HeaderChapter(
    chapterHide: Boolean,
    component: BookConstructorComponent
) {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!chapterHide) {
            Text(
                text = MainRes.string.chapters,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 8.dp,
                        bottom = 0.dp
                    ),
                color = Theme.colors.primaryAction,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h2,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.weight(1F))

        }

        Icon(
            modifier = Modifier
                .padding(
                    top = 12.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 0.dp
                )
                .size(20.dp)
                .clickable {
                    component.hideChapters(!chapterHide)
                },
            imageVector = if (chapterHide) {
                FeatherIcons.ArrowRight
            } else {
                FeatherIcons.ArrowLeft
            },
            contentDescription = null,
            tint = Theme.colors.primaryAction,
        )
    }
}