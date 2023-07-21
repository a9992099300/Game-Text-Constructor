package com.a9992099300.gameTextConstructor.data.auth.repository

import com.a9992099300.gameTextConstructor.data.auth.models.AuthResponseBody
import com.a9992099300.gameTextConstructor.data.common.request
import com.a9992099300.gameTextConstructor.data.auth.services.AuthService
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.common.simpleRequest

class AuthRepositoryImpl(
    private val authService: AuthService
): AuthRepository {
    override suspend fun registration(email: String, password: String): Result<AuthResponseBody?> =
        request {
            authService.registration(email, password)
        }

    override suspend fun login(email: String, password: String): Result<AuthResponseBody?> =
        request {
            authService.login(email, password)
        }

    override suspend fun saveTokens(body: AuthResponseBody): Result<Unit?> = simpleRequest {
        authService.saveTokens(body)
    }
}