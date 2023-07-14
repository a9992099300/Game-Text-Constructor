package com.a9992099300.gameTextConstructor.data.profile.repository


import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.profile.models.ProfileDataModel

interface UserRepository {

    suspend fun updateUserInfo(name: String) : Result<ProfileDataModel>

    suspend fun getUserInfo() : Result<ProfileDataModel>
}