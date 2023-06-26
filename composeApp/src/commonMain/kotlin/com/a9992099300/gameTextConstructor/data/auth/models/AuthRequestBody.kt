package com.a9992099300.gameTextConstructor.data.auth.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AuthRequestBody(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("returnSecureToken") val returnSecureToken: Boolean,
)
