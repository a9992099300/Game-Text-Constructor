package com.a9992099300.gameTextConstructor.data.profile.services

import com.a9992099300.gameTextConstructor.data.profile.models.ProfileDataModel
import io.ktor.client.statement.HttpResponse

interface UserService{
     suspend fun getUserInfo(userId: String) : HttpResponse

     suspend fun updateUserInfo(model: ProfileDataModel) : HttpResponse
}