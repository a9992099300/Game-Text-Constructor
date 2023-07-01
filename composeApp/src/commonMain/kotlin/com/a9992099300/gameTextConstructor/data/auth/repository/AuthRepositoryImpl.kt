package com.a9992099300.gameTextConstructor.data.auth.repository

import com.a9992099300.gameTextConstructor.data.common.request
import com.a9992099300.gameTextConstructor.data.auth.services.AuthService

class AuthRepositoryImpl(
    private val authService: AuthService
): AuthRepository {
    override suspend fun registration(email: String, password: String) =
        request {
            authService.registration(email, password)
        }

    override suspend fun login(email: String, password: String) =
        request {
            authService.login(email, password)
        }
}