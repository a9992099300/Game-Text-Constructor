package com.a9992099300.gameTextConstructor.data.auth.services

import com.a9992099300.gameTextConstructor.data.auth.models.AuthResponseBody

interface AuthService{
     suspend fun registration(email: String, password: String): AuthResponseBody

     suspend fun login(email: String, password: String): AuthResponseBody

     suspend fun saveTokens(body: AuthResponseBody)
}