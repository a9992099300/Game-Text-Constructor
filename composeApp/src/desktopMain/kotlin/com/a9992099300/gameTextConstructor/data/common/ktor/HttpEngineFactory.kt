package com.a9992099300.gameTextConstructor.data.common.ktor

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

internal actual class HttpEngineFactory actual constructor() {
    actual fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
}