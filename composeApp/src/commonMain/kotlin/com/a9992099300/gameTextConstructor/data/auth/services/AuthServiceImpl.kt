package com.a9992099300.gameTextConstructor.data.auth.services

import com.a9992099300.gameTextConstructor.data.auth.models.AuthRequestBody
import com.a9992099300.gameTextConstructor.data.auth.models.AuthResponseBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path

class AuthServiceImpl(
    private val httpClient: HttpClient
) : AuthService {
    override suspend fun registration(email: String, password: String): AuthResponseBody =
        httpClient.post {
            url {
                path("accounts:signUp")
                setBody(
                    AuthRequestBody(
                        email,
                        password,
                        true
                    )
                )
            }
        }.body()


    override suspend fun login(email: String, password: String): AuthResponseBody =
        httpClient.post {
            url {
                path("accounts:signInWithPassword")
                setBody(
                    AuthRequestBody(
                        email,
                        password,
                        true
                    )
                )
            }
        }.body()
}