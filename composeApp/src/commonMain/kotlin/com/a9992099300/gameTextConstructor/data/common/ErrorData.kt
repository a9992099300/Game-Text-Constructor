package com.a9992099300.gameTextConstructor.data.common

import kotlinx.serialization.Serializable


@Serializable
data class ErrorData(
    val error: ErrorMain
) {
    @Serializable
    data class ErrorMain(
        val code: Int,
        val errors: List<Error>,
        val message: String
    ) {
        @Serializable
        data class Error(
            val domain: String,
            val message: String,
            val reason: String
        )
    }
}