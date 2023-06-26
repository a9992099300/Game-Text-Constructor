package com.a9992099300.gameTextConstructor.data.common.ktor

import io.ktor.client.engine.*

internal expect class HttpEngineFactory constructor() {
    fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig>
}