package com.a9992099300.gameTextConstructor.data.common.ktor

import com.a9992099300.gameTextConstructor.data.auth.models.AuthRefreshBody
import com.a9992099300.gameTextConstructor.data.auth.models.AuthResponseBody
import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import io.github.xxfast.kstore.KStore
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


internal val ktorModuleDatabase = DI.Module("ktorModuleDatabase") {
    bind<HttpClient>(tag = "database") with singleton {
        HttpClient(HttpEngineFactory().createEngine()) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL

            }

            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    })
            }

//            install(Auth) {
//                bearer {
//                    loadTokens {
//                        val store = instance<KStore<SavedAuth>>().get()?.lastOrNull()
//                        val accessToken = store?.idToken ?: ""
//                        val refreshToken = store?.refreshToken ?: ""
//                        Napier.d(message = "accessToken $accessToken", tag = log)
//
//                        BearerTokens(accessToken, refreshToken)
//                    }
//                    sendWithoutRequest {
//                        true
//                    }
//                }
//            }

            install(Auth) {
                bearer {
                    refreshTokens {
                        val store = instance<KStore<SavedAuth>>()
                        val token = store.get()?.lastOrNull()?.refreshToken
                        val refreshTokenInfo: AuthRefreshBody = client.submitForm(
                            url = "https://securetoken.googleapis.com/v1/token?key=${KEY_VALUE}",
                            formParameters = parameters {
                                append("grant_type", "refresh_token")
                                append("refresh_token", token ?: "")
                            }
                        ) { markAsRefreshTokenRequest() }.body()

                        store.delete()
                        store.set(
                            setOf(
                                AuthResponseBody(
                                    idToken = refreshTokenInfo.idToken,
                                    email = "",
                                    refreshToken = refreshTokenInfo.refreshToken,
                                    localId = refreshTokenInfo.userId,
                                    expiresIn = refreshTokenInfo.expiresIn,
                                    registered = null
                                )
                            )
                        )
                     null
                    }
                }
            }

            install(DefaultRequest)

            install(HttpTimeout) {
                connectTimeoutMillis = 15000
                requestTimeoutMillis = 30000
            }

            defaultRequest {
                url("https://game-text-constructor-default-rtdb.europe-west1.firebasedatabase.app/")
                header("Content-Type", "application/json; charset=UTF-8")
            }
        }
    }
}

const val AUTH = "auth"
