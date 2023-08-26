package com.a9992099300.gameTextConstructor.logic.constructor.createPage

import com.a9992099300.gameTextConstructor.data.auth.services.log
import com.a9992099300.gameTextConstructor.data.books.models.ItemPage
import com.a9992099300.gameTextConstructor.data.books.repository.pages.PagesRepository
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.common.StateUi.*
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.CoroutineContext
import com.a9992099300.gameTextConstructor.ui.screen.models.PageUIModel
import com.a9992099300.gameTextConstructor.utils.allowChangeValue
import com.a9992099300.gameTextConstructor.utils.getLastPartId
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateOrEditPageComponentImpl(
    private val componentContext: ComponentContext,
    private val pageRepository: PagesRepository = Inject.instance(),
    private val bookId: String,
    private val chapterId: String,
    private val sceneId: String,
    private val pageId: String,
    override val onBack: () -> Unit,
    private val onCreateAction: (String) -> Unit,
    private val onSaveChanged: () -> Unit,
) : ComponentContext by componentContext, CreateOrEditPageComponent {


    override val stateUi: StateFlow<StateUi<Unit>>
        get() = createPageViewModel.stateUi.asStateFlow()

    override val uiPageModel: StateFlow<PageUIModel>
        get() = createPageViewModel.uiPageModel.asStateFlow()

    override val uiActions: StateFlow<List<ItemPage>>
        get() = createPageViewModel.uiActions.asStateFlow()

    override val pageIds: StateFlow<List<String>>
        get() = createPageViewModel.pageIds.asStateFlow()

    override fun changeTitle(title: String) {
        createPageViewModel.changeTitle(title)
    }

    override fun savePage() {
        if (pageId.isBlank()) {
            createPageViewModel.createPage()
        } else {
            createPageViewModel.editPage()
        }
    }

    override fun changeDescription(description: String) {
        createPageViewModel.changeDescription(description)
    }

    override fun onCreateAction() {
        this.onCreateAction()
    }

    override fun resetError() {
        createPageViewModel.resetError()
    }

    private val createPageViewModel =
        instanceKeeper.getOrCreate { CreatePageViewModel(Dispatchers.Default) }

    inner class CreatePageViewModel(mainContext: CoroutineContext) :
        InstanceKeeper.Instance {

        private val scope = CoroutineScope(mainContext + SupervisorJob())

        val uiPageModel: MutableStateFlow<PageUIModel> =
            MutableStateFlow(PageUIModel())

        val uiActions: MutableStateFlow<List<ItemPage>> =
            MutableStateFlow(listOf())

        val stateUi: MutableStateFlow<StateUi<Unit>> =
            MutableStateFlow(Initial)

        val pageIds: MutableStateFlow<List<String>> =
            MutableStateFlow(listOf())

        init {
            getPageIds()
            if (pageId.isNotBlank()) getPage()
        }

        fun createPage() {
            scope.launch {
                stateUi.value = Loading
                val id = pageIds.value.lastOrNull().getLastPartId() ?: "0"

                val result = pageRepository.addPage(
                    uiPageModel.value.mapToData(
                        bookId,
                        chapterId,
                        sceneId,
                        id.toString()
                    )
                )
                when (result) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            stateUi.value = Success(Unit)
                            onSaveChanged()
                        }
                    }

                    is Result.Error -> stateUi.value = Error(result.error?.message ?: "")
                }
            }
        }

        fun editPage() {
            scope.launch {
                stateUi.value = Loading
                val result = pageRepository.addPage(
                    uiPageModel.value.mapToData()
                )
                when (result) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            stateUi.value = Success(Unit)
                            onSaveChanged()
                        }
                    }

                    is Result.Error -> {
                        Napier.d(message = "error ${result.error?.message}",  tag = log)
                        stateUi.value = Error(result.error?.message ?: "")
                    }
                }
            }
        }

        private fun getPage() {
            scope.launch {
                stateUi.value = Loading

                val result = pageRepository.getPage(
                    bookId,
                    chapterId,
                    sceneId,
                    pageId
                )
                when (result) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            uiPageModel.emit(result.value.mapToUI())
                        }
                    }
                    is Result.Error -> stateUi.emit(Error(result.error?.message ?: ""))
                }
            }
        }

        private fun getPageIds() {
            scope.launch {
                stateUi.value = Loading
                when (val result = pageRepository.getPagesIds(bookId, chapterId, sceneId)) {
                    is Result.Success -> {
                        pageIds.emit(result.value)
                        stateUi.emit(Success(Unit))
                    }

                    is Result.Error -> stateUi.emit(Error(result.error?.message ?: ""))
                }
            }
        }

        fun changeTitle(title: String) {
            title.allowChangeValue<Unit>(
                64,
                allowSetValue = {
                    uiPageModel.value = uiPageModel.value.copy(title = title)
                },
                errorSetValue = { error ->
                    stateUi.value = (error as Error).copy(codeError = Companion.ERROR_TITLE)
                }
            )
        }

        fun changeDescription(description: String) {
            description.allowChangeValue<Unit>(
                1000,
                allowSetValue = {
                    uiPageModel.value = uiPageModel.value.copy(description = description)
                },
                errorSetValue = { error ->
                    stateUi.value = (error as Error).copy(codeError = Companion.ERROR_DESCRIPTION)
                }
            )
        }

       fun resetError() {
            scope.launch {
                stateUi.emit(Success(Unit))
            }
        }

        override fun onDestroy() {
            scope.cancel()
        }
    }
}