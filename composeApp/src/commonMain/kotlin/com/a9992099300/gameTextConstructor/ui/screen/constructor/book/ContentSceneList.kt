package com.a9992099300.gameTextConstructor.ui.screen.constructor.book

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
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
import compose.icons.feathericons.ArrowDown
import compose.icons.feathericons.ArrowUp


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ContentSceneList(
    component: BookConstructorComponent,
    sceneHide: Boolean,
) {
    val scenes by component.scenes.collectAsState()

    HeaderScene(component, sceneHide)

    when (val currentState = scenes) {
        is StateUi.Success -> {
            LazyRow(
                modifier = Modifier.padding(4.dp),
                contentPadding = PaddingValues(
                    top = 8.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Column {

                        AnimatedContent(
                            sceneHide,
                            transitionSpec = {
                                if (sceneHide) {
                                    slideInVertically { height -> height } + fadeIn() with
                                            slideOutVertically { height -> height } + fadeOut()
                                } else {
                                    slideInVertically { height -> -height } + fadeIn() with
                                            slideOutVertically { height -> height } + fadeOut()
                                }
                            }
                        ) { targetState ->
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
                                        component.hideScenes(!sceneHide)
                                    },
                                imageVector = if (sceneHide) {
                                    FeatherIcons.ArrowDown
                                } else {
                                    FeatherIcons.ArrowUp
                                },
                                contentDescription = null,
                                tint = Theme.colors.primaryAction,
                            )
                        }

                    }
                }
                items(
                    items = currentState.value,
                    key = { it.sceneId }
                ) { scene ->
                    CommonCard(
                        modifier = Modifier
                            .width(220.dp)
                            .fillMaxHeight()
                            .clickable {
                                component.loadPages(scene.sceneId)
                            },
                        title = scene.title.ifBlank { MainRes.string.no_name },
                        number = MainRes.string.scene_number_v
                            .format(scene.sceneNumber.toString()),
                        description = scene.description,
                        imageUrl = scene.imageUrl,
                        selected = scene.selected,
                        sceneHide = sceneHide,
                        onEdit = {
                            component.onEditScene(scene)
                        }
                    )
                }
                item {
                    if (currentState !is StateUi.Error && !sceneHide) {
                        CommonPlusCard(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(220.dp)
                                .clickable {
                                    component.onCreateScene()
                                },
                            text = MainRes.string.add_scene
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
            CommonSnackBar<Unit>(
                message = currentState.messageError,
                dismissSnack = {
                    component.loadChapters()
                }
            )
        }

        is StateUi.Initial -> {

        }
    }
}


@Composable
fun HeaderScene(
    component: BookConstructorComponent,
    sceneHide: Boolean
) {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!sceneHide) {
            Text(
                text = MainRes.string.scenes,
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
    }
}
