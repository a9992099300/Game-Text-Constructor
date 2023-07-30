package com.a9992099300.gameTextConstructor.ui.screen.constructor.book

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.logic.constructor.book.BookConstructorComponent
import com.a9992099300.gameTextConstructor.ui.widgets.HeaderText


@Composable
fun BookScreen(component: BookConstructorComponent) {
    val chapterHide by component.chapterHide.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
    ) {
        BookHeader(component)

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(if (chapterHide) 100.dp else 300.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(50.dp, 0.dp, 8.dp, 0.dp)
                ) {
                    ContentChaptersList(component, chapterHide)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp, 0.dp, 12.dp, 8.dp)
                ) {
                    ContentSceneList(component)
                }

                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp, 8.dp, 12.dp, 0.dp)
                ) {
                    ContentPageList(component)
                }
            }
        }
    }
}

@Composable
fun BookHeader(
    component: BookConstructorComponent
) {
    val title by component.title.collectAsState()

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(50.dp, 0.dp, 8.dp, 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {

            HeaderText(
                text = title,
            )

            Spacer(modifier = Modifier.weight(1f))

//            Icon(
//                modifier = Modifier.size(24.dp).clickable {
//
//                },
//                imageVector = Icons.Default.Refresh, contentDescription = null,
//                tint = Theme.colors.primaryAction,
//            )
        }
    }
}




