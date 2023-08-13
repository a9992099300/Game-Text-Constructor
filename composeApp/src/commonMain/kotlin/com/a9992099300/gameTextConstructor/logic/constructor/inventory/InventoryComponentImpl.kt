package com.a9992099300.gameTextConstructor.logic.constructor.inventory

import com.a9992099300.gameTextConstructor.data.books.repository.inventory.InventoryRepository
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.ui.screen.models.Inventory
import com.a9992099300.gameTextConstructor.ui.screen.models.InventoryUIModel
import com.a9992099300.gameTextConstructor.ui.screen.models.TypeInventory
import com.a9992099300.gameTextConstructor.ui.screen.models.TypeInventoryUiModel
import com.a9992099300.gameTextConstructor.utils.allowChangeValue
import com.a9992099300.gameTextConstructor.utils.allowChangeValueInt
import com.a9992099300.gameTextConstructor.utils.getIdInventory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class InventoryComponentImpl(
    private val componentContext: ComponentContext,
    private val bookId: String,
    private val inventoryRepository: InventoryRepository,
    override val onBack: () -> Unit,
) : ComponentContext by componentContext, InventoryComponent {

    private val inventoryViewModel =
        instanceKeeper.getOrCreate { InventoryViewModel(Dispatchers.Default) }

    init {
        refresh()
    }

    override val inventoryState: StateFlow<StateUi<List<InventoryUIModel>>>
        get() = inventoryViewModel.inventoryState.asStateFlow()

    override val currentInventory: StateFlow<StateUi<InventoryUIModel>>
        get() = inventoryViewModel.currentInventory.asStateFlow()

    override val inventoryIds: StateFlow<List<String>>
        get() = inventoryViewModel.inventoryIds.asStateFlow()

    override val stateType: StateFlow<List<TypeInventoryUiModel>>
        get() = inventoryViewModel.stateType.asStateFlow()

    override val stateUi: StateFlow<StateUi<Unit>>
        get() = inventoryViewModel.stateUi.asStateFlow()


    override fun refresh() {
        inventoryViewModel.initInventory()
    }

    override fun changeTitle(title: String) {
        inventoryViewModel.changeTitle(title)
    }

    override fun changeDescription(description: String) {
        inventoryViewModel.changeDescription(description)
    }

    override fun changeQuantity(quantity: String) {
        inventoryViewModel.changeQuantity(quantity)
    }

    override fun addOrEditInventory() {
        val newInventoryModel = currentInventory.value as? StateUi.Success ?: return
        if (newInventoryModel.value.inventoryId.isNotBlank()) {
            inventoryViewModel.editInventory(newInventoryModel.value)
        } else {
            inventoryViewModel.addInventory(newInventoryModel.value)
        }
    }

    override fun createInventory() {
        inventoryViewModel.createEmptyInventory()
    }

    override fun selectInventory(model: InventoryUIModel) {
        inventoryViewModel.selectInventory(model)
    }

    override fun resetError() {
        inventoryViewModel.resetError()
    }

    override fun deleteInventory(inventoryId: String) {
        inventoryViewModel.deleteInventory(inventoryId)
    }


    override fun chooseType(type: TypeInventory) {
        inventoryViewModel.chooseType(type)
    }


    private inner class InventoryViewModel(mainContext: CoroutineContext) :
        InstanceKeeper.Instance {

        private val scope = CoroutineScope(mainContext + SupervisorJob())

        val currentInventory: MutableStateFlow<StateUi<InventoryUIModel>> =
            MutableStateFlow(StateUi.Initial)

        val inventoryState: MutableStateFlow<StateUi<List<InventoryUIModel>>> =
            MutableStateFlow(StateUi.Initial)

        val inventoryIds: MutableStateFlow<List<String>> =
            MutableStateFlow(listOf())

        val stateType: MutableStateFlow<List<TypeInventoryUiModel>> =
            MutableStateFlow(Inventory.listDefaultType)

        val stateUi: MutableStateFlow<StateUi<Unit>> =
            MutableStateFlow(StateUi.Initial)

        fun initInventory() {
            scope.launch {
                inventoryState.emit(StateUi.Loading)
                loadInventory()
            }
        }

        fun resetError() {
            scope.launch {
                stateUi.emit(StateUi.Success(Unit))
            }
        }

        fun loadInventory() {
            scope.launch {
                val result = inventoryRepository.getInventories(bookId)
                when (result) {
                    is Result.Success -> {
                        inventoryState.emit(StateUi.Success(result.value.map {
                            it.mapToUI()
                        }))
                    }

                    is Result.Error -> {
                        inventoryState.emit(StateUi.Error(result.error?.message ?: "Error"))
                    }
                }
            }
        }

        fun addInventory(model: InventoryUIModel) {
            scope.launch {
                currentInventory.emit(StateUi.Loading)

                val modelWithId = model.copy(
                    inventoryId = model.title.getIdInventory()
                )
                val result = inventoryRepository
                    .addInventory(
                        bookId, modelWithId.mapToData(
                            bookId,
                            stateType.value.find { it.selected }?.typeInventory
                                ?: TypeInventory.THING
                        )
                    )
                when (result) {
                    is Result.Success -> {
                        loadInventory()
                        currentInventory.emit(
                            StateUi.Success(result.value.mapToUI())
                        )
                    }

                    is Result.Error -> {
                        inventoryState.emit(
                            StateUi.Error(result.error?.message ?: "Error")
                        )
                    }
                }
            }
        }

        fun editInventory(model: InventoryUIModel) {

            scope.launch {
                currentInventory.emit(StateUi.Loading)
                val result = inventoryRepository
                    .addInventory(
                        bookId, model.mapToData(
                            stateType.value.find { it.selected }?.typeInventory
                                ?: TypeInventory.THING
                        )
                    )
                when (result) {
                    is Result.Success -> {
                        currentInventory.emit(
                            StateUi.Success(result.value.mapToUI())
                        )
                        loadInventory()
                    }

                    is Result.Error -> {
                        inventoryState.emit(
                            StateUi.Error(result.error?.message ?: "Error")
                        )
                    }
                }
            }
        }

        fun changeTitle(title: String) {
            val currentModel = currentInventory.value as? StateUi.Success ?: return
            scope.launch {
                title.allowChangeValue<Unit>(
                    34,
                    allowSetValue = {
                        inventoryViewModel.currentInventory.emit(
                            StateUi.Success(
                                currentModel.value.copy(title = title)
                            )
                        )

                    },
                    errorSetValue = { error ->
                        stateUi.emit(
                            (error as StateUi.Error).copy(codeError = StateUi.ERROR_TITLE)
                        )
                    }
                )
            }
        }

        fun changeDescription(description: String) {
            val currentModel = currentInventory.value as? StateUi.Success ?: return
            scope.launch {
                description.allowChangeValue<Unit>(
                    54,
                    allowSetValue = {
                        inventoryViewModel.currentInventory.emit(
                            StateUi.Success(
                                currentModel.value.copy(description = description)
                            )
                        )

                    },
                    errorSetValue = { error ->
                        stateUi.emit(
                            (error as StateUi.Error).copy(codeError = StateUi.ERROR_DESCRIPTION)
                        )
                    }
                )
            }
        }

        fun changeQuantity(quantity: String) {
            val currentModel = currentInventory.value as? StateUi.Success ?: return
            scope.launch {
                quantity.allowChangeValueInt<Unit>(
                    54,
                    allowSetValue = { number ->
                        inventoryViewModel.currentInventory.emit(
                            StateUi.Success(
                                currentModel.value.copy(defaultQuantity = number)
                            )
                        )

                    },
                    errorSetValue = { error ->
                        stateUi.value =
                            (error as StateUi.Error).copy(codeError = StateUi.ERROR_DESCRIPTION)
                    }
                )
            }
        }

        fun createEmptyInventory() {
            scope.launch {
                currentInventory.emit(StateUi.Success(InventoryUIModel(typeInventory = TypeInventory.THING)))
            }
        }

        fun selectInventory(inventory: InventoryUIModel) {
            val listInventory =
                (inventoryState.value as? StateUi.Success)?.value?.map { model ->
                    model.copy(selected = model.inventoryId == inventory.inventoryId)
                }
            scope.launch {
                currentInventory.emit(StateUi.Success(inventory))
                stateType.emit(
                    getListTypes(inventory.typeInventory)
                )
                listInventory?.let {
                    inventoryState.emit(StateUi.Success(it))
                }
            }
        }

        fun deleteInventory(inventoryId: String) {
            scope.launch {
                when (val result = inventoryRepository.deleteInventory(bookId, inventoryId)) {
                    is Result.Success -> {
                        loadInventory()
                    }

                    is Result.Error -> {
                        inventoryState.emit(
                            StateUi.Error(result.error?.message ?: "Error")
                        )
                    }
                }
            }
        }

        fun chooseType(type: TypeInventory) {
            scope.launch {
                stateType.emit(
                    getListTypes(type)
                )
            }
        }

        private fun getListTypes(type: TypeInventory) =
            Inventory.listDefaultType.map {
                val model = if (it.typeInventory == type) {
                    it.copy(selected = true)
                } else {
                    it
                }
                model
            }

        override fun onDestroy() {
            scope.cancel()
        }
    }

}