package com.a9992099300.gameTextConstructor.data.common

import com.a9992099300.gameTextConstructor.utils.ApplicationName
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import net.harawata.appdirs.AppDirsFactory

internal actual object StorageFactory {
    actual fun createStorage(): KStore<SavedAuth> =
        storeOf(pathTo("auth"), emptySet())

    actual fun pathTo(id: String): String {
        val appDir = AppDirsFactory.getInstance().getUserDataDir(
            ApplicationName,
            "1.0.0",
            "com.a9992099300"
        )
        return "$appDir/$id.json"
    }
}


