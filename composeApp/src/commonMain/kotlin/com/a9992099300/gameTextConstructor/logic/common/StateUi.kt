package com.a9992099300.gameTextConstructor.logic.common

sealed class StateUi<out T> {
    data class Success<out T>(
        val value: T
    ) : StateUi<T>()

    open class Error(val messageError: String) : StateUi<Nothing>()

    object Empty : StateUi<Nothing>()

    object Initial : StateUi<Nothing>()

    object Loading : StateUi<Nothing>()
}