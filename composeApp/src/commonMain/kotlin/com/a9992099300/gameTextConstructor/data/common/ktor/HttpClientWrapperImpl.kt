package com.a9992099300.gameTextConstructor.data.common.ktor

import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import io.github.xxfast.kstore.KStore
import io.ktor.client.HttpClient
import io.ktor.http.parameters
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class HttpClientWrapperImpl(
    private val store: KStore<SavedAuth>,
    private val httpClient: HttpClient,
) : HttpClientWrapper {
    var token = ""

    init {
        MainScope().launch {
            token = store.get()?.firstOrNull()?.idToken ?: ""
        }
    }

    override val addToken = httpClient.apply {
        parameters {
            append(AUTH, token)
        }
    }

}