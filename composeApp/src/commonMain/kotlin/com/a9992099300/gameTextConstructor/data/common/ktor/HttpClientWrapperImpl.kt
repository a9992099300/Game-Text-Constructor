package com.a9992099300.gameTextConstructor.data.common.ktor

import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import com.a9992099300.gameTextConstructor.di.PlatformConfiguration
import com.a9992099300.gameTextConstructor.finish
import io.github.xxfast.kstore.KStore
import io.ktor.client.HttpClient
import io.ktor.http.parameters
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class HttpClientWrapperImpl(
    private val store: KStore<SavedAuth>,
    private val httpClient: HttpClient,
    private val platformConfiguration: PlatformConfiguration
) : HttpClientWrapper {
    private var token = ""
    private var localId = ""

    init {
        MainScope().launch {
            val authData = store.get()?.firstOrNull()
            token = authData?.idToken ?: ""
            localId = authData?.localId ?: ""
        }
    }

    override val addToken = httpClient.apply {
        parameters {
            append(AUTH, token)
        }
    }

    override fun logout() {
        MainScope().launch {
            store.delete()
            finish(platformConfiguration)
        }
    }

}