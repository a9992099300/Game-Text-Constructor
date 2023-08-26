package com.a9992099300.gameTextConstructor.logic.constructor.action

import com.a9992099300.gameTextConstructor.data.books.models.ItemPage
import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import kotlinx.coroutines.flow.StateFlow

interface CreateOrEditActionComponent: BaseComponent<Unit> {

    val uiItemPageModel: StateFlow<ItemPage>

}