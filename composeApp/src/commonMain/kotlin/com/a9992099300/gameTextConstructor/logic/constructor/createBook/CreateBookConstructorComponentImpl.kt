package com.a9992099300.gameTextConstructor.logic.constructor.createBook

import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateBookConstructorComponentImpl(
    private val componentContext: ComponentContext,
//    private val message: String,
     private val onBack: () -> Unit,
): ComponentContext by componentContext, CreateBookConstructorComponent {

    override val stateUi: StateFlow<StateUi<Unit>>  =
        MutableStateFlow(StateUi.Initial)

    override fun onBackClicked() {
        onBack()
    }
}