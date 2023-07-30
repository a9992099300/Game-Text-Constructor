package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.theme.Theme


@Composable
fun CommonCard(
    modifier: Modifier,
    title: String = "",
    description: String  = "",
    imageUrl: String  = "",
    selected: Boolean = false,
    simpleView: Boolean = false
) {
    Card(
        modifier = modifier,
        shape =  RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.surface,
        border = if (selected) {
            BorderStroke(2.dp, Theme.colors.primaryBackground)
        } else {
            null
        }
    ) {
        Column(
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            PostCommonHeader(title, simpleView)

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
               //     .verticalScroll(rememberScrollState())
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
            ) {
                Text(
                    text = description,
                    color = Theme.colors.primaryTextColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun PostCommonHeader(title: String, simpleView: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 0.dp),
    ) {
        if (!simpleView) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.End)
                    .clickable {

                    },
                imageVector = Icons.Default.Edit,
                contentDescription = Icons.Default.Settings.name,
                tint = Theme.colors.primaryAction,
            )

            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth(),
                color = Theme.colors.primaryAction,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.body1,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
//        Text(
//            text = book.category,
//            color = Theme.colors.hintTextColor
//        )}
        }
    }
}

