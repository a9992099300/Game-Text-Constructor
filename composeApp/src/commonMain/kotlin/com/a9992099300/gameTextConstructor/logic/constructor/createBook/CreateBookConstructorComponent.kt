package com.a9992099300.gameTextConstructor.logic.constructor.createBook

import com.a9992099300.gameTextConstructor.logic.common.StateUi
import kotlinx.coroutines.flow.StateFlow

interface CreateBookConstructorComponent {

    val stateUi: StateFlow<StateUi<Unit>>

    fun onBackClicked()
}