package com.a9992099300.gameTextConstructor.di.inventory

import com.a9992099300.gameTextConstructor.data.books.repository.inventory.InventoryRepository
import com.a9992099300.gameTextConstructor.data.books.repository.inventory.InventoryRepositoryImpl
import com.a9992099300.gameTextConstructor.data.books.services.inventary.InventoryService
import com.a9992099300.gameTextConstructor.data.books.services.inventary.InventoryServiceImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal val inventoryModule = DI.Module("inventoryModule") {
    bindProvider<InventoryService>() { InventoryServiceImpl(instance()) }
    bindProvider<InventoryRepository>() { InventoryRepositoryImpl(instance(), instance()) }
    }
