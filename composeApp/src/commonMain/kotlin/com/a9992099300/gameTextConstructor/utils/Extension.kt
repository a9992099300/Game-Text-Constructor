package com.a9992099300.gameTextConstructor.utils

import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import kotlinx.datetime.Clock

inline fun <T>String.allowChangeValue(size: Int, allowSetValue: () -> Unit, errorSetValue: (StateUi<T>) -> Unit) {
     if (this.length < size) {
        allowSetValue()
    } else {
         errorSetValue(
             StateUi.Error(MainRes.string.max_string.format(size.toString()))
         )
    }
}

inline fun <T>String.allowChangeValueInt(size: Int, allowSetValue: (Int) -> Unit, errorSetValue: (StateUi<T>) -> Unit) {
    if (this.length < size && this.toIntOrNull() != null || this.isEmpty()) {
        allowSetValue(this.toInt())
    } else {
        errorSetValue(
            StateUi.Error(MainRes.string.max_int.format(size.toString()))
        )
    }
}

fun String?.getLastPartId() : Int? =
    this?.trim('_')
        ?.lastOrNull()
        ?.digitToIntOrNull()
        ?.plus(1)

fun String.getIdInventory() : String =
    this.replace(" ", "_") + "_${Clock.System.now().toEpochMilliseconds()}"

