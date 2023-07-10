package com.a9992099300.gameTextConstructor.data.profile.models

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDataModel(
    val id: String,
    val name: String,
    val email: String,
    val prem: Boolean
)