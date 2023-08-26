package com.a9992099300.gameTextConstructor.logic.constructor.action

import com.a9992099300.gameTextConstructor.data.books.models.ItemPage
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.common.StateUi.Initial
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
    private val pageId: String,
    override val onBack: () -> Unit,
) : ComponentContext by componentContext, CreateOrEditActionComponent {

    override val stateUi: StateFlow<StateUi<Unit>>
        get() = createPageViewModel.stateUi.asStateFlow()

    override val uiItemPageModel: StateFlow<ItemPage>
        get() = createPageViewModel.uiItemPageModel.asStateFlow()


    private val createPageViewModel =
        instanceKeeper.getOrCreate { CreateBooksListRetainedInstance(Dispatchers.Default) }

    inner class CreateBooksListRetainedInstance(mainContext: CoroutineContext) :
        InstanceKeeper.Instance {

        private val scope = CoroutineScope(mainContext + SupervisorJob())

        val uiItemPageModel: MutableStateFlow<ItemPage> =
            MutableStateFlow(ItemPage())

        val stateUi: MutableStateFlow<StateUi<Unit>> =
            MutableStateFlow(Initial)

        override fun onDestroy() {
            scope.cancel()
        }
    }
}