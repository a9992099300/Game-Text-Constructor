package com.a9992099300.gameTextConstructor.data.common.ktor

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton


internal val ktorModuleDatabase = DI.Module("ktorModuleDatabase") {
//    importOnce(databaseModule)
    bind<HttpClient>(tag = "database") with singleton {
        HttpClient(HttpEngineFactory().createEngine()) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

//            install(Auth) {
//                bearer {
//                    // Load tokens ...
//                    refreshTokens {
//                        val database = instance<MyDatabase>()
//                        val authData: List<AuthDb> = database.authQueries.selectAll().executeAsList()
//                        val refreshTokenInfo: AuthRefreshBody = client.submitForm(
//                            url = "https://securetoken.googleapis.com/v1/token?key=[${KEY_VALUE}]",
//                            formParameters = parameters {
//                                append("grant_type", "refresh_token")
//                                //   append("client_id", System.getenv("GOOGLE_CLIENT_ID"))
//                                append("refresh_token", authData.last().refresh_token ?: "")
//                            }
//                        ) { markAsRefreshTokenRequest() }.body()
//                        database.authQueries.deletAll()
//                        database.authQueries.insert(
//                            refreshTokenInfo.idToken,
//                            refreshTokenInfo.refreshToken,
//                            "",
//                            refreshTokenInfo.userId,
//                            refreshTokenInfo.expiresIn
//                        )
//                        BearerTokens(
//                            refreshTokenInfo.idToken,
//                            refreshTokenInfo.refreshToken,
//                        )
//                    }
//                }
//            }

            install(DefaultRequest)
            install(ContentNegotiation) {
                json(
                    Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }

            install(HttpTimeout) {
                connectTimeoutMillis = 15000
                requestTimeoutMillis = 30000
            }

            defaultRequest {
//                val database = instance<MyDatabase>()
//                val authData2: List<AuthDb> = database.authQueries.selectAll().executeAsList()
//                url.parameters.append(AUTH, authData2.last().access_token ?: "")
                url("https://game-text-constructor-default-rtdb.europe-west1.firebasedatabase.app/")
                header("Content-Type", "application/json; charset=UTF-8")
            }
        }
    }
}

const val AUTH = "auth"
