package com.a9992099300.gameTextConstructor.logic.constructor.dialog

import com.arkivanov.decompose.ComponentContext

class DefaultDialogComponentImpl(
    private val componentContext: ComponentContext,
    private val message: String,
    private val onDismissed: () -> Unit,
    private val onAccept: () -> Unit,
) : DefaultDialogComponent, ComponentContext by componentContext {

    override fun onDismissClicked() {
        onDismissed()
    }

    override fun onAcceptClicked() {
        onAccept()
    }
}