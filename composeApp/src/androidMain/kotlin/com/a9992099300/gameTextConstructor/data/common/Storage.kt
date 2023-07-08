package com.a9992099300.gameTextConstructor.data.common

import com.a9992099300.gameTextConstructor.di.Inject.instance
import com.a9992099300.gameTextConstructor.di.PlatformConfiguration
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf

internal actual object StorageFactory {
  actual fun createStorage(): KStore<SavedAuth> =
    storeOf(pathTo("auth"), emptySet())
  actual fun pathTo(id: String): String {
    val platform: PlatformConfiguration = instance()
    return "${platform.context.filesDir.path}/$id.json"
  }
}