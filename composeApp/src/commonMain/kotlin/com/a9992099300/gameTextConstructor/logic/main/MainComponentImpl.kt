package com.a9992099300.gameTextConstructor.logic.main

import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow

class MainComponentImpl(
    componentContext: ComponentContext,
    override val onBack: () -> Unit,
) : ComponentContext by componentContext, MainComponent {

    override val stateUi: MutableStateFlow<StateUi<Unit>> =
        MutableStateFlow(StateUi.Initial)
}
