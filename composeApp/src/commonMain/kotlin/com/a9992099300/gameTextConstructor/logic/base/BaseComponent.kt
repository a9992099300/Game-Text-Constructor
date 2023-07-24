package com.a9992099300.gameTextConstructor.logic.base

import com.a9992099300.gameTextConstructor.logic.common.StateUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface BaseComponent<T> {

    val onBack: () -> Unit

    val stateUi: StateFlow<StateUi<T>>

    fun closeSnack() {
        (stateUi as? MutableStateFlow<StateUi<T>>)?.value = StateUi.Initial
    }
    fun onBackClicked() {
        onBack()
    }

}