package com.a9992099300.gameTextConstructor.ui.screen.constructor.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.data.auth.services.log
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.widgets.CommonCard
import com.a9992099300.gameTextConstructor.ui.widgets.CommonPlusCard
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import io.github.aakira.napier.Napier


@Composable
fun ContentSceneList(
    component: BookConstructorComponent
) {
    val scenes by component.scenes.collectAsState()
    Napier.d(message = "scenes $scenes", tag = log)
        when (val currentState = scenes) {
            is StateUi.Success -> {
                LazyRow (
                    modifier = Modifier.padding(4.dp),
                    contentPadding = PaddingValues(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = currentState.value ,
                        key = { it.sceneId}
                    ) { scene ->
                        CommonCard(
                            modifier = Modifier
                                .width(220.dp)
                                .fillMaxHeight()
                                .clickable {
                                  component.loadPages(scene.sceneId)
                                },
                            title = scene.title.ifBlank { "Нет названия" },
                            description = scene.description,
                            imageUrl = scene.imageUrl,
                            selected = scene.selected
                        )
                    }
                    item {
                        if (currentState !is StateUi.Error) {
                            CommonPlusCard(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(220.dp)
                                    .clickable {
                                    // component.createNewBook()
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
}
