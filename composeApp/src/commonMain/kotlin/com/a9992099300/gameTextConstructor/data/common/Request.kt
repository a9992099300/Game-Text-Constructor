package com.a9992099300.gameTextConstructor.data.common

import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.data.auth.services.log
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json

suspend inline fun <reified R> request(
    request: () -> HttpResponse?
): Result<R> {
    val result: HttpResponse?
    return try {
        result = request()

        when {
            ((200..207).contains(result?.status?.value) && result != null) -> {
                Result.Success(result.body())
            }
            result?.status?.value == 401 -> {
               // request()
                Result.Error(Throwable(message = MainRes.string.error_auth))
            }
            else -> {
                val stringBody: String? = result?.body()
                val error = stringBody?.let { Json.decodeFromString<ErrorData>(it) }
                Result.Error(Throwable(message = error?.error?.message ?: "Error"))
            }
        }
    } catch (e: Exception) {
        Napier.d("Exception ${e}", tag = log)
        Result.Error(e)
    }
}

inline fun <reified R> simpleRequest(
    request: () -> R?
): Result<R> =
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

inline fun <T> String?.allowRequest(request: (String) -> T): T? =
    if (this != null) {
        request(this)
    } else {
        null
    }