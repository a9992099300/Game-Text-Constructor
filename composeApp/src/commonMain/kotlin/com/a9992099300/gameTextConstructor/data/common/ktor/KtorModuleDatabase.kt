package com.a9992099300.gameTextConstructor.data.common.ktor

import com.a9992099300.gameTextConstructor.di.login.kStoreModule
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


internal val ktorModuleDatabase = DI.Module("ktorModuleAuth") {
    importOnce(kStoreModule)
    bind<HttpClient>() with singleton {
        HttpClient(HttpEngineFactory().createEngine()) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            install(DefaultRequest)
//https://www.appsloveworld.com/coding/ios/337/refresh-auth-token-in-ktor-for-ios-http-client
// https://github.com/ktorio/ktor-documentation/blob/2.3.2/codeSnippets/snippets/client-auth-oauth-google/src/main/kotlin/com/example/Application.kt

//            install(Auth) {
//                bearer {
//                    CoroutineScope(Dispatchers.Default).launch {
//                        val authData: AuthResponseBody? =
//                            instance<KStore<SavedAuth>>().get()?.firstOrNull()
//                        BearerTokens(
//                            authData?.idToken ?: "",
//                            authData?.refreshToken ?: "")
//                    }
//                }
//            }

            install(ContentNegotiation) {
                json(Json {
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
                url("https://identitytoolkit.googleapis.com/v1/")
                url.parameters.append(KEY, KEY_VALUE)
                header("Content-Type", "application/json; charset=UTF-8")
            }

        }

    }
}
