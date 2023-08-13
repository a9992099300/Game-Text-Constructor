package com.a9992099300.gameTextConstructor.ui.screen.constructor.inventory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.screen.models.InventoryUIModel


@Composable
fun InventoryItem(
    modifier: Modifier,
    model: InventoryUIModel,
    imageUrl: String = "",
    onEdit: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.surface,
        border = if (model.selected) {
            BorderStroke(2.dp, Theme.colors.primaryBackground)
        } else {
            null
        }
    ) {
        Column(
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            InventoryItemHeader(model) {

            }

            Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                ) {
                    Text(
                        text = model.description,
                        color = Theme.colors.primaryTextColor,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
    }
}

@Composable
private fun InventoryItemHeader(
    model: InventoryUIModel,
    onEdit: () -> Unit
) {

        Column {
            Text(
                text = model.title,
                modifier = Modifier
                    .fillMaxWidth(),
                color = Theme.colors.primaryAction,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = MainRes.string.def_quan_inventory.format(model.defaultQuantity.toString()),
                modifier = Modifier
                    .fillMaxWidth().padding(0.dp, 4.dp),
                color = Theme.colors.primaryAction,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
}


