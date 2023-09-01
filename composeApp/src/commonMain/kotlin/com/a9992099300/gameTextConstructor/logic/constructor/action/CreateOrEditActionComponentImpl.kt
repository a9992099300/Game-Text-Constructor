package com.a9992099300.gameTextConstructor.logic.constructor.action

import com.a9992099300.gameTextConstructor.data.books.repository.actions.ActionRepository
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.common.StateUi.Initial
import com.a9992099300.gameTextConstructor.ui.screen.models.ActionTypeUI
import com.a9992099300.gameTextConstructor.ui.screen.models.ItemPageUi
import com.a9992099300.gameTextConstructor.utils.allowChangeValue
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
import kotlin.coroutines.CoroutineContext

class CreateOrEditActionComponentImpl(
    private val componentContext: ComponentContext,
    private val actionRepository: ActionRepository = Inject.instance(),
    private val bookId: String,
    private val chapterId: String,
    private val sceneId: String,
    private val pageId: String,
    private val actionId: String,
    override val onBack: () -> Unit,
) : ComponentContext by componentContext, CreateOrEditActionComponent {

    override val stateUi: StateFlow<StateUi<Unit>>
        get() = createItemPageViewModel.stateUi.asStateFlow()

    override val uiItemPageModel: StateFlow<ItemPageUi>
        get() = createItemPageViewModel.uiItemPageModel.asStateFlow()
    override val actionType: StateFlow<List<ActionTypeUI>>
        get() = createItemPageViewModel.uiActionType.asStateFlow()

    override fun changeDescription(description: String) {
        createItemPageViewModel.changeDescription(description)
    }


    private val createItemPageViewModel =
        instanceKeeper.getOrCreate { CreateItemViewModel(Dispatchers.Default) }

    inner class CreateItemViewModel(mainContext: CoroutineContext) :
        InstanceKeeper.Instance {

        private val scope = CoroutineScope(mainContext + SupervisorJob())

        val uiItemPageModel: MutableStateFlow<ItemPageUi> =
            MutableStateFlow(ItemPageUi())

        val uiActionType: MutableStateFlow<List<ActionTypeUI>> =
            MutableStateFlow(ActionTypeUI.defaultListAction.map {
               val item =  if (it is ActionTypeUI.Move) {
                    it.copy(selected = true)
                } else it
                item
            })

        val stateUi: MutableStateFlow<StateUi<Unit>> =
            MutableStateFlow(Initial)

        private fun getPage() {
//            scope.launch {
//                stateUi.value = StateUi.Loading
//
//                val result = pageRepository.getPage(
//                    bookId,
//                    chapterId,
//                    sceneId,
//                    pageId
//                )
//                when (result) {
//                    is Result.Success -> {
//                        withContext(Dispatchers.Main) {
//                            uiActionType.emit(
//
//                               // result.value.mapToUI().action
//                            )
//                        }
//                    }
//
//                    is Result.Error -> stateUi.emit(StateUi.Error(result.error?.message ?: ""))
//                }
//            }
        }

        fun changeDescription(description: String) {
            description.allowChangeValue<Unit>(
                1000,
                allowSetValue = {
                    uiItemPageModel.value = uiItemPageModel.value.copy(description = description)
                },
                errorSetValue = { error ->
                    stateUi.value = (error as StateUi.Error).copy(codeError = StateUi.ERROR_DESCRIPTION)
                }
            )
        }

        override fun onDestroy() {
            scope.cancel()
        }
    }
}