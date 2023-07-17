package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.data.books.models.BookDataModel
import com.a9992099300.gameTextConstructor.theme.Theme


@Composable
fun CommonBookCard(
    modifier: Modifier,
    book: BookDataModel,
) {
    Card(
        modifier = modifier.height(400.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box( //image
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(
                        color = Theme.colors.secondaryBackground
                    )
            )
            Spacer(modifier = Modifier.height(8.dp))

            PostHeader(book)

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = book.description,
                    color = Theme.colors.primaryTextColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
private fun PostHeader(book: BookDataModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = book.title,
            modifier = Modifier
                .fillMaxWidth(),
            color = Theme.colors.primaryAction,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.body1,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = book.category,
            color = Theme.colors.hintTextColor
        )
    }
}

