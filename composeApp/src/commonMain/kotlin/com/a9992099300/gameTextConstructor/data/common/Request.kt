package com.a9992099300.gameTextConstructor.data.common

import com.a9992099300.gameTextConstructor.data.auth.services.log
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified R>request(
    request: () -> HttpResponse?
) : Result<R> =
    try {
        val result = request()
        if ((200..207).contains(result?.status?.value) &&
            result != null
        ) {
            Result.Success(result.body())
        } else {
            Result.Error(result?.body())
        }
    } catch (e: Exception) {
        Napier.d("Exception ${e}", tag = log)
        Result.Error(e)
    }

inline fun <reified R> simpleRequest(
    request: () -> R?
) : Result<R> =
    try {
        val result = request()
        if (result != null) {
            Result.Success(result)
        } else {
            Result.Error(IllegalArgumentException())
        }
    } catch (e: Exception) {
        Napier.d("Exception ${e} mes ${e.message} cause ${e.cause}", tag = log)
        Result.Error(e)
    }

inline fun <T>String?.allowRequest(request: (String) -> T): T? =
    if (this != null) {
        request(this)
    } else {
        null
    }