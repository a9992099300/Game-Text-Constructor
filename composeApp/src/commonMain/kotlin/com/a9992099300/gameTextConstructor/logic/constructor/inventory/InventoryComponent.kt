package com.a9992099300.gameTextConstructor.logic.constructor.inventory

import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.ui.screen.models.InventoryUIModel
import com.a9992099300.gameTextConstructor.ui.screen.models.TypeInventory
import com.a9992099300.gameTextConstructor.ui.screen.models.TypeInventoryUiModel
import kotlinx.coroutines.flow.StateFlow

interface InventoryComponent: BaseComponent<Unit> {

    val inventoryState: StateFlow<StateUi<List<InventoryUIModel>>>

    val inventoryIds: StateFlow<List<String>>

    val currentInventory: StateFlow<StateUi<InventoryUIModel>>

    val stateType: StateFlow<List<TypeInventoryUiModel>>

    fun refresh()

    fun changeTitle(title: String)

    fun changeDescription(description: String)

    fun changeQuantity(quantity: String)

    fun addOrEditInventory()

    fun createInventory()

    fun selectInventory(model: InventoryUIModel)

    fun resetError()

    fun deleteInventory(inventoryId: String)

    fun chooseType(type: TypeInventory)

}