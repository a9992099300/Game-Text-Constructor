package com.a9992099300.gameTextConstructor.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.constructor.listBooks.ListBookConstructorComponent
import com.a9992099300.gameTextConstructor.theme.Theme


@Composable
fun CommonPlusCard(
    modifier: Modifier,
    component: ListBookConstructorComponent
) {
    Card(modifier = modifier.height(400.dp)) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier.size(50.dp).clickable {
                   // component.getBooksList()
                },
                imageVector = Icons.Default.Add, contentDescription = null,
                tint = Theme.colors.primaryAction,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = MainRes.string.add_book,
                color = Theme.colors.primaryAction,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                fontSize = 20.sp
            )
        }
    }
}

