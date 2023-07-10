package com.a9992099300.gameTextConstructor.data.profile.services

import com.a9992099300.gameTextConstructor.data.profile.models.ProfileDataModel

interface UserService{
     suspend fun getUserInfo()

     suspend fun updateUserInfo(model: ProfileDataModel)
}