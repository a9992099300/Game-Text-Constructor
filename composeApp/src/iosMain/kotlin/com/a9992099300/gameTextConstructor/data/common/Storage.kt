package com.a9992099300.gameTextConstructor.data.common

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import platform.Foundation.NSHomeDirectory

internal actual object StorageFactory {
  actual fun createStorage(): KStore<SavedAuth> = storeOf(pathTo("auth"), emptySet())
  actual fun pathTo(id: String): String = "${NSHomeDirectory()}/$id.json"
}