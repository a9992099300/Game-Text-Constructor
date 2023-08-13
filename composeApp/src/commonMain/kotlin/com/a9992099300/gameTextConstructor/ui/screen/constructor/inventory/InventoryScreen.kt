package com.a9992099300.gameTextConstructor.ui.screen.constructor.inventory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.constructor.inventory.InventoryComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.widgets.CommonPlusCard
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import com.a9992099300.gameTextConstructor.ui.widgets.HeaderText


@Composable
fun InventoryScreen(component: InventoryComponent) {
    val stateUi by component.stateUi.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
    ) {
        InventoryHeader(component)

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(50.dp, 0.dp, 8.dp, 0.dp)
                ) {
                    ContentInventory(component)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                CreateOrEditInventoryContent(
                    component
                )
            }
        }
    }

    if (stateUi is StateUi.Error) {
        CommonSnackBar(
            message = (stateUi as StateUi.Error).messageError,
            component = component as? BaseComponent<Unit>,
            dismissSnack = {
                component.resetError()
            }
        )
    }
}

@Composable
fun ContentInventory(component: InventoryComponent) {

    val inventoryState by component.inventoryState.collectAsState()

    when (val currentState = inventoryState) {
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

                item {
                    CommonPlusCard(
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth()
                            .clickable {
                                component.createInventory()
                            },
                        text = MainRes.string.add_inventory
                    )
                }
                items(
                    items = currentState.value,
                    key = { it.inventoryId }
                ) { item ->

                    InventoryItem(
                        modifier = Modifier
                            .height(120.dp)
                            .clickable {
                                component.selectInventory(item)
                            },
                        model = item,
                        imageUrl = item.imageUrl,
                        onEdit = {}
                    )
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
                    component.resetError()
                }
            )
        }

        is StateUi.Initial -> {

        }
    }
}


@Composable
fun InventoryHeader(
    component: InventoryComponent
) {

    Card(
        modifier = Modifier
            .height(85.dp)
            .fillMaxWidth()
            .padding(50.dp, 0.dp, 8.dp, 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            HeaderText(
                text = MainRes.string.edit_inventory,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier
                    .padding(
                        top = 0.dp,
                        start = 0.dp,
                        end = 24.dp,
                        bottom = 0.dp
                    )
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                         component.refresh()
                    },
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = Theme.colors.primaryAction,
            )

            Icon(
                modifier = Modifier.size(24.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        component.onBackClicked()
                    },
                imageVector = Icons.Default.ArrowBack, contentDescription = null,
                tint = Theme.colors.primaryAction,
            )
        }
    }
}