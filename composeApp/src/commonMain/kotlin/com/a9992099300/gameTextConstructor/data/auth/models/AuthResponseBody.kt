package com.a9992099300.gameTextConstructor.data.auth.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AuthResponseBody(
    @SerialName("idToken") val idToken: String,
    @SerialName("email") val email: String,
    @SerialName("refreshToken") val refreshToken: String,
    @SerialName("expiresIn") val expiresIn: String,
    @SerialName("localId") val localId: String,
)
