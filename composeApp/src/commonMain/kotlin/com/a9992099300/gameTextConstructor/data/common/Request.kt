package com.a9992099300.gameTextConstructor.data.common

inline fun <R>request(
    request: () -> R?
) : Result<R?> =
    try {
        val result = request()
        if (result == null) {
            Result.Empty
        } else {
            Result.Success(request())
        }
    } catch (e: Exception) {
        Result.Error(e)
    }