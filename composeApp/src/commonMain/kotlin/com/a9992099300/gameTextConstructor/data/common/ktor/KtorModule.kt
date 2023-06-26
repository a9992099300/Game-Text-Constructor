package com.a9992099300.gameTextConstructor.data.common.ktor

import com.a9992099300.gameTextConstructor.data.common.ktor.HttpEngineFactory
import io.ktor.client.*
//import io.ktor.client.features.*
//import io.ktor.client.features.json.*
//import io.ktor.client.features.json.serializer.*
//import io.ktor.client.features.logging.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.kotlinx.serializer.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton


const val ApiKey = "AIzaSyBSg1OrmYOuDpO6FqvLc_gPbQD9PwkGinw"

internal val ktorModule = DI.Module("ktorModule") {
    bind<HttpClient>() with singleton {
        HttpClient(HttpEngineFactory().createEngine()) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

//            install(JsonFeature) {
//                serializer = KotlinxSerializer(json = instance())
//            }

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
                url("https://identitytoolkit.googleapis.com/v1/")
                header("Content-Type", "application/json; charset=UTF-8")
            }
//https://rapidapi.com/Josiassejod1-WNU9Fi2D8/api/marvel-vs-capcom-2/
            //https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=[API_KEY]
        }

    }
}