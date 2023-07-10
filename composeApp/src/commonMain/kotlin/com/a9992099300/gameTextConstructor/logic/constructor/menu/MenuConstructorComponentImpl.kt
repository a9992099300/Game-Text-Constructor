package com.a9992099300.gameTextConstructor.logic.constructor.menu

import com.a9992099300.gameTextConstructor.logic.constructor.menu.models.ItemModel
import com.arkivanov.decompose.ComponentContext

class MenuConstructorComponentImpl(
    componentContext: ComponentContext,
    private val openScreen: (ItemModel) -> Unit,
): MenuConstructorComponent {

    override fun openItem(item: ItemModel) {
        openScreen(item)
    }

}