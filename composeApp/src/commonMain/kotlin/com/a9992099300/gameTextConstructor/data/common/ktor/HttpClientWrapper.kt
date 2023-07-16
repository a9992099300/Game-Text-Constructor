package com.a9992099300.gameTextConstructor.data.common.ktor

import io.ktor.client.HttpClient

interface HttpClientWrapper {
    val addToken: HttpClient
    fun logout()
}
