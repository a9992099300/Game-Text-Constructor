package com.a9992099300.gameTextConstructor.data.profile.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDataModel(
    @SerialName("userId") val userId: String,
    @SerialName("name")  val name: String,
    @SerialName("email") val email: String
)