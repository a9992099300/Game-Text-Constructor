package com.a9992099300.gameTextConstructor.data.common

import com.a9992099300.gameTextConstructor.data.auth.models.AuthResponseBody
import io.github.xxfast.kstore.KStore

internal expect object StorageFactory {
     fun createStorage(): KStore<SavedAuth>
     fun pathTo(id: String): String
}


typealias SavedAuth = Set<AuthResponseBody>
