package com.a9992099300.gameTextConstructor.data.common

import com.a9992099300.gameTextConstructor.data.auth.services.log
import io.github.aakira.napier.Napier

inline fun <R>request(
    request: () -> R?
) : Result<R> =
    try {
        val result = request()
        if (result == null) {
            Result.Error(IllegalArgumentException())
        } else {
            Result.Success(request()!!)
        }
    } catch (e: Exception) {
        Napier.d("Exception ${e}", tag = log)
        Result.Error(e)
    }

inline fun <T>String?.allowRequest(request: (String) -> T): T? =
    if (this != null) {
        request(this)
    } else {
        null
    }