package com.a9992099300.gameTextConstructor.data.auth.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AuthRequestBody(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("returnSecureToken") val returnSecureToken: Boolean,
)

@Parcelize
@Serializable
data class AuthRefreshBody(
    @SerialName("id_token") val idToken: String,
    @SerialName("project_id") val projectId: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("expires_in") val expiresIn: String,
    @SerialName("user_id") val userId: String,
    @SerialName("token_type") val tokenType: String,
): Parcelable
