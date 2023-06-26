package com.a9992099300.gameTextConstructor.data.auth.services

import com.a9992099300.gameTextConstructor.data.auth.models.AuthResponseBody

interface AuthService{
     suspend fun signIn(email: String, password: String): AuthResponseBody
}