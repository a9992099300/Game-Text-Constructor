package com.a9992099300.gameTextConstructor.logic.common

sealed class StateUi<out T> {
    data class Success<out T>(
        val value: T
    ) : StateUi<T>()

    data class Error(val messageError: String, val codeError: Int = 0) : StateUi<Nothing>()

    object Initial : StateUi<Nothing>()

    object Loading : StateUi<Nothing>()

    companion object{
        const val ERROR_TITLE = 1001
        const val ERROR_DESCRIPTION= 1002
    }

}

