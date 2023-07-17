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
    bind<HttpClient>(tag = "database") with singleton {
        HttpClient(HttpEngineFactory().createEngine()) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            install(DefaultRequest)
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
                url("https://game-text-constructor-default-rtdb.europe-west1.firebasedatabase.app/")
                header("Content-Type", "application/json; charset=UTF-8")
            }
        }
    }
}

const val AUTH = "auth"
