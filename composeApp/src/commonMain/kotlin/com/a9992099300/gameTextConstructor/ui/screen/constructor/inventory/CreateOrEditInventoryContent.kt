package com.a9992099300.gameTextConstructor.ui.screen.constructor.inventory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.constructor.inventory.InventoryComponent
import com.a9992099300.gameTextConstructor.theme.Theme
import com.a9992099300.gameTextConstructor.ui.screen.models.InventoryUIModel
import com.a9992099300.gameTextConstructor.ui.screen.models.TypeInventory
import com.a9992099300.gameTextConstructor.ui.screen.models.TypeInventoryUiModel
import com.a9992099300.gameTextConstructor.ui.widgets.CommonSnackBar
import com.a9992099300.gameTextConstructor.ui.widgets.CommonTextFieldOutline
import com.a9992099300.gameTextConstructor.ui.widgets.HeaderText
import compose.icons.FeatherIcons
import compose.icons.feathericons.Save


@Composable
fun CreateOrEditInventoryContent(component: InventoryComponent) {

    val currentInventory by component.currentInventory.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, 0.dp, 12.dp, 0.dp)
    ) {
        when (val state = currentInventory) {
            is StateUi.Success -> {
                contentEditInventory(state, component)
            }

            is StateUi.Error -> {
                CommonSnackBar<Unit>(
                    message = state.messageError,
                    dismissSnack = {
                        component.resetError()
                    }
                )
            }

            is StateUi.Initial -> {}

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

        }
    }

}

@Composable
private fun contentEditInventory(
    currentInventory: StateUi.Success<InventoryUIModel>,
    component: InventoryComponent
) {

            EditInventoryHeader(
                component,
                currentInventory.value
            )
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopStart,
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {


                    CommonTextFieldOutline(
                        text = currentInventory.value.title,
                        hint = MainRes.string.inventory_title,
                        onValueChanged = {
                            component.changeTitle(it)
                        },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ChooseType(
                        component,
                        onClickCategory = {
                            component.chooseType(it)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))


                    CommonTextFieldOutline(
                        text = currentInventory.value.defaultQuantity.toString(),
                        hint = MainRes.string.inventory_def_quantity,
                        onValueChanged = {
                            component.changeQuantity(it)
                        },
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CommonTextFieldOutline(
                        text = currentInventory.value.description,
                        hint = MainRes.string.inventory_description,
                        onValueChanged = {
                            component.changeDescription(it)
                        },
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChooseType(component: InventoryComponent, onClickCategory:(TypeInventory) -> Unit) {
    val stateType by component.stateType.collectAsState()

    Column {
        FlowRow(
            Modifier
                .fillMaxWidth(1f)
                .wrapContentHeight(align = Alignment.Top),
            horizontalArrangement = Arrangement.Start,
        ) {
            stateType.forEach {
                InventoryFilterChip(it,onClickCategory)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryFilterChip(categoryUiModel: TypeInventoryUiModel, onClick: (TypeInventory) -> Unit) {
    FilterChip(
        modifier = Modifier.padding(4.dp),
        selected = categoryUiModel.selected,
        onClick = {
            onClick.invoke(categoryUiModel.typeInventory)
        },
        label = { Text(categoryUiModel.title) },
        colors = FilterChipDefaults.filterChipColors(
            selectedLabelColor = Theme.colors.secondaryTextColor,
            selectedContainerColor = Theme.colors.primaryAction
        ),
        leadingIcon = if (categoryUiModel.selected) {
            {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                    tint = Theme.colors.secondaryTextColor
                )
            }
        } else {
            null
        }
    )
}

@Composable
 fun EditInventoryHeader(
    component: InventoryComponent,
    model: InventoryUIModel,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        HeaderText(
            modifier = Modifier.padding(16.dp)
                .align(Alignment.CenterVertically),
            text = if (model.inventoryId.isNotBlank())
                MainRes.string.edit_inventory_1 else MainRes.string.add_inventory
        )

        Spacer(modifier = Modifier.weight(1f))


        Icon(
            modifier = Modifier
                .padding(16.dp)
                .size(24.dp)
                .clickable {
                    component.addOrEditInventory()
                },
            imageVector = FeatherIcons.Save,
            contentDescription = FeatherIcons.Save.name,
            tint = Theme.colors.primaryAction,
        )
        if (model.inventoryId.isNotBlank()) {
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
                    .clickable {
                        component.deleteInventory(model.inventoryId)
                    },
                imageVector = Icons.Default.Delete,
                contentDescription = Icons.Default.Delete.name,
                tint = Theme.colors.primaryAction,
            )
        }
    }
}