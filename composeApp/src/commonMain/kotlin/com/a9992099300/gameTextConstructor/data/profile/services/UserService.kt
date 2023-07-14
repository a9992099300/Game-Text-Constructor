package com.a9992099300.gameTextConstructor.data.profile.services

import com.a9992099300.gameTextConstructor.data.profile.models.ProfileDataModel

interface UserService{
     suspend fun getUserInfo(userId: String) : ProfileDataModel

     suspend fun updateUserInfo(model: ProfileDataModel) : ProfileDataModel
}