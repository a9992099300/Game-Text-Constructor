package com.a9992099300.gameTextConstructor.data.auth.services

import com.a9992099300.gameTextConstructor.data.auth.models.AuthResponseBody
import io.ktor.client.statement.HttpResponse

interface AuthService{
     suspend fun registration(email: String, password: String): HttpResponse

     suspend fun login(email: String, password: String): HttpResponse

     suspend fun saveTokens(body: AuthResponseBody)
}