package com.a9992099300.gameTextConstructor.logic.constructor.createScenes

import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.data.books.repository.scenes.ScenesRepository
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.common.StateUi.*
import com.a9992099300.gameTextConstructor.logic.common.StateUi.Companion.ERROR_DESCRIPTION
import com.a9992099300.gameTextConstructor.logic.common.StateUi.Companion.ERROR_INPUT_NUMBER
import com.a9992099300.gameTextConstructor.logic.common.StateUi.Companion.ERROR_TITLE
import com.a9992099300.gameTextConstructor.ui.screen.models.SceneUIModel
import com.a9992099300.gameTextConstructor.utils.allowChangeValue
import com.a9992099300.gameTextConstructor.utils.allowChangeValueInt
import com.a9992099300.gameTextConstructor.utils.getLastPartId
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import com.a9992099300.gameTextConstructor.data.common.Result
import kotlinx.coroutines.withContext

class CreateOrEditScenesComponentImpl(
    private val componentContext: ComponentContext,
    private val bookId: String,
    private val chapterId: String,
    private val onSceneEdited: () -> Unit,
    override val onBack: () -> Unit,
    override val editeSceneModel: String
) : ComponentContext by componentContext, CreateOrEditScenesComponent {

    private val scenesRepository: ScenesRepository = Inject.instance()

    override val stateUi: MutableStateFlow<StateUi<Unit>> = MutableStateFlow(Initial)
    override val sceneState: MutableStateFlow<SceneUIModel> =
        MutableStateFlow(
            if (editeSceneModel.isNotBlank()) {
                SceneUIModel()
             //   editeSceneModel
            } else {
                SceneUIModel()
            }
        )

    override val scenesIds: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf())

    override val snackBar: MutableStateFlow<String> =
        MutableStateFlow("")


    override fun changeTitle(title: String) {
        title.allowChangeValue<Unit>(
            64,
            allowSetValue = {
                this.sceneState.value = sceneState.value.copy(title = title)
            },
            errorSetValue = { error ->
                stateUi.value = (error as Error).copy(codeError = ERROR_TITLE)
            }
        )
    }

    override fun changeNumber(title: String) {
        title.allowChangeValueInt<Unit>(
            10,
            allowSetValue = {
                this.sceneState.value = sceneState.value
                    .copy(sceneNumber = it)
            },
            errorSetValue = { error ->
                stateUi.value = (error as Error).copy(codeError = ERROR_INPUT_NUMBER)
            }
        )
    }

    override fun changeDescription(description: String) {
        description.allowChangeValue<Unit>(
            1000,
            allowSetValue = {
                this.sceneState.value = sceneState.value.copy(description = description)
            },
            errorSetValue = { error ->
                stateUi.value = (error as Error).copy(codeError = ERROR_DESCRIPTION)
            }
        )
    }

    override fun addOrEditScene() {
        if (editeSceneModel.isBlank()) {
            createChapterRetainedInstance.addScene()
        } else {
            createChapterRetainedInstance.editScene()
        }
    }

    override fun resetError() {
        stateUi.value = Success(Unit)
    }

    override fun deleteScene() {
        if (sceneState.value.deletable) {
            createChapterRetainedInstance.deleteScene()
        } else {
            stateUi.value = Error(MainRes.string.prohibit_delete_book)
        }
    }


    private val createChapterRetainedInstance =
        instanceKeeper.getOrCreate { CreateBooksListRetainedInstance(Dispatchers.Default) }

    inner class CreateBooksListRetainedInstance(mainContext: CoroutineContext) :
        InstanceKeeper.Instance {

        private val scope = CoroutineScope(mainContext + SupervisorJob())

        init {
            getSceneIds()
        }

        fun addScene() {
            scope.launch {
                stateUi.value = Loading
                val id = scenesIds.value.lastOrNull().getLastPartId() ?: "0"

                    val result = scenesRepository.addScene(
                        sceneState.value.mapToData(
                            bookId,
                            chapterId,
                            id.toString()
                        )
                    )
                    when (result) {
                        is Result.Success -> {
                            withContext(Dispatchers.Main) {
                                onSceneEdited()
                                stateUi.value = Success(Unit)
                            }
                        }

                        is Result.Error -> stateUi.value = Error(result.error?.message ?: "")
                    }
                }
        }

        fun editScene() {
            scope.launch {
                stateUi.value = Loading
                val result = scenesRepository.addScene(
                    sceneState.value.mapToData()
                )
                when (result) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            onSceneEdited()
                            stateUi.value = Success(Unit)
                        }
                    }

                    is Result.Error -> stateUi.value = Error(result.error?.message ?: "")
                }
            }
        }

        private fun getSceneIds() {
            scope.launch {
                stateUi.value = Loading
                when (val result = scenesRepository.getSceneIds(bookId, chapterId)) {
                    is Result.Success -> {
                        scenesIds.value = result.value
                        stateUi.value = Success(Unit)
                    }
                    is Result.Error -> stateUi.value = Error(result.error?.message ?: "")
                }
            }
        }

        fun deleteScene() {
            scope.launch {
                stateUi.value = Loading
                when (val result = scenesRepository
                    .deleteScene(bookId, sceneState.value.chapterId, sceneState.value.sceneId)) {
                    is Result.Success -> {
                        stateUi.value = Success(Unit)
                        onSceneEdited()
                    }

                    is Result.Error -> stateUi.value = Error(result.error?.message ?: "")
                }
            }
        }

        override fun onDestroy() {
            scope.cancel()
        }
    }
}