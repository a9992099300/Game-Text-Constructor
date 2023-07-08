package com.a9992099300.gameTextConstructor.data.common

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf

internal actual object StorageFactory {
  actual fun createStorage(): KStore<SavedAuth> =
    storeOf("setting", emptySet())
  actual fun pathTo(id: String): String = ""
}