package com.a9992099300.gameTextConstructor.data.auth.repository

import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.auth.models.AuthResponseBody

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<AuthResponseBody?>
}