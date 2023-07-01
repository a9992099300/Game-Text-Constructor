package com.a9992099300.gameTextConstructor.data.auth.repository

import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.auth.models.AuthResponseBody

interface AuthRepository {
    suspend fun registration(email: String, password: String): Result<AuthResponseBody?>

    suspend fun login(email: String, password: String): Result<AuthResponseBody?>
}