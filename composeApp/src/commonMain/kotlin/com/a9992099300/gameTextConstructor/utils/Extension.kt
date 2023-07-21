package com.a9992099300.gameTextConstructor.utils

import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.logic.common.StateUi

inline fun <T>String.sizeAllow(size: Int, allowSetValue: () -> Unit, errorSetValue: (StateUi<T>) -> Unit) {
     if (this.length < size) {
        allowSetValue()
    } else {
         errorSetValue(
             StateUi.Error(MainRes.string.max_string.format(this.length.toString()))
         )
    }
}