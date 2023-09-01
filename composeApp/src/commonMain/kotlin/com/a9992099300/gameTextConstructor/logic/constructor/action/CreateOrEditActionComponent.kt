package com.a9992099300.gameTextConstructor.logic.constructor.action

import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.ui.screen.models.ActionTypeUI
import com.a9992099300.gameTextConstructor.ui.screen.models.ItemPageUi
import kotlinx.coroutines.flow.StateFlow

interface CreateOrEditActionComponent: BaseComponent<Unit> {

    val uiItemPageModel: StateFlow<ItemPageUi>

    val actionType: StateFlow<List<ActionTypeUI>>

    fun changeDescription(description: String)

}