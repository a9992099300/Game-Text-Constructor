package com.a9992099300.gameTextConstructor.data.auth.services

import com.a9992099300.gameTextConstructor.data.auth.models.AuthRequestBody
import com.a9992099300.gameTextConstructor.data.auth.models.AuthResponseBody
import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import io.github.aakira.napier.Napier
import io.github.xxfast.kstore.KStore
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path

const val log = "myLogCommon"

class AuthServiceImpl(
    private val httpClient: HttpClient,
    private val store: KStore<SavedAuth>
) : AuthService {
    override suspend fun registration(email: String, password: String) =
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
        }


    override suspend fun login(email: String, password: String) =
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
        }

    suspend fun updateToken(email: String, password: String) =
        httpClient.post {
            url {
                path("token")
                setBody(
                    AuthRequestBody(
                        email,
                        password,
                        true
                    )
                )
            }
        }

    override suspend fun saveTokens(body: AuthResponseBody) {
        Napier.d(message = "saveTokens log $body", tag = log)
        store.set(setOf(body))
    }
}