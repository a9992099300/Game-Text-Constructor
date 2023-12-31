package com.a9992099300.gameTextConstructor.ui.screen.constructor.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.widgets.CommonCard
import com.a9992099300.gameTextConstructor.ui.widgets.CommonPlusCard
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar


@Composable
fun ContentPageList(
    component: BookConstructorComponent
) {
    val pages by component.pages.collectAsState()

        when (val currentState = pages) {
            is StateUi.Success -> {
                LazyColumn  (
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(4.dp),
                    contentPadding = PaddingValues(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = currentState.value ,
                        key = { it.pageId}
                    ) { page ->
                        CommonCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clickable {
                                    component.onEditPage(page.pageId)
                                },
                            title = page.title.ifBlank { MainRes.string.no_name },
                            description = page.description,
                            imageUrl = page.imageUrl,
                            onEdit = {
                                component.onEditPage(page.pageId)
                            }
                        )
                    }
                    item {
                        if (currentState !is StateUi.Error) {
                            CommonPlusCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clickable {
                                     component.onCreatePage()
                                },
                                text = MainRes.string.add_page
                            )
                        }
                    }
                }
            }

            is StateUi.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
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
