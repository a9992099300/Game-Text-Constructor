package com.a9992099300.gameTextConstructor.di.login

import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import com.a9992099300.gameTextConstructor.data.common.StorageFactory
import io.github.xxfast.kstore.KStore
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton


internal val kStoreModule = DI.Module("kStoreModule") {
    bind<KStore<SavedAuth>>() with singleton  {
        StorageFactory.createStorage()
    }
}

//https://github.com/xxfast/KStore#configurations



