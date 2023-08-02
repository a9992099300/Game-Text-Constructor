package com.a9992099300.gameTextConstructor.logic.constructor.createChapter

import com.a9992099300.gameTextConstructor.MainRes
import com.a9992099300.gameTextConstructor.data.books.repository.BooksRepository
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.logic.common.StateUi.*
import com.a9992099300.gameTextConstructor.logic.common.StateUi.Companion.ERROR_DESCRIPTION
import com.a9992099300.gameTextConstructor.logic.common.StateUi.Companion.ERROR_INPUT_NUMBER
import com.a9992099300.gameTextConstructor.logic.common.StateUi.Companion.ERROR_TITLE
import com.a9992099300.gameTextConstructor.ui.screen.models.ChapterUIModel
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
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CreateChapterComponentImpl(
    private val componentContext: ComponentContext,
    private val bookId: String,
    private val onChapterCreate: () -> Unit,
    override val onBack: () -> Unit,
    override val editedChapterModel: ChapterUIModel?
) : ComponentContext by componentContext, CreateChapterComponent {

    private val booksRepository: BooksRepository = Inject.instance()
    override val stateUi: MutableStateFlow<StateUi<Unit>> = MutableStateFlow(Initial)
    override val chapterState: MutableStateFlow<ChapterUIModel> =
        MutableStateFlow(editedChapterModel ?: ChapterUIModel())

    override val chapterIds: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf())

    override val snackBar: MutableStateFlow<String> =
        MutableStateFlow("")


    override fun changeTitle(title: String) {
        title.allowChangeValue<Unit>(
            64,
            allowSetValue = {
                this.chapterState.value = chapterState.value.copy(title = title)
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
                this.chapterState.value = chapterState.value
                    .copy(chapterNumber = title.toIntOrNull() ?: 0)
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
                this.chapterState.value = chapterState.value.copy(description = description)
            },
            errorSetValue = { error ->
                stateUi.value = (error as Error).copy(codeError = ERROR_DESCRIPTION)
            }
        )
    }

    override fun addChapter() {
        if (editedChapterModel == null) {
            createChapterRetainedInstance.addChapter()
        } else {
            createChapterRetainedInstance.editChapters()
        }
    }

    override fun deleteChapter() {

        if (chapterState.value.deletable) {
            createChapterRetainedInstance.deleteChapter()
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
            getChapters()
        }

        fun addChapter() {
            scope.launch {
                stateUi.value = Loading
                val id = chapterIds.value.lastOrNull().getLastPartId()

                if (id == null) {
                    stateUi.value = Error("Error")
                } else {
                    val result = booksRepository.addChapter(
                        chapterState.value.mapToData(
                            bookId,
                            id.toString()
                        )
                    )
                    when (result) {
                        is Result.Success -> {
                            withContext(Dispatchers.Main) {
                                onChapterCreate()
                            }
                        }

                        is Result.Error -> stateUi.value = Error(result.error?.message ?: "")
                    }
                }
            }
        }

        fun editChapters() {
            scope.launch {
                stateUi.value = Loading
                val result = booksRepository.addChapter(
                    chapterState.value.mapToData()
                )
                when (result) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            onChapterCreate()
                        }
                    }

                    is Result.Error -> stateUi.value = Error(result.error?.message ?: "")
                }
            }
        }

        private fun getChapters() {
            scope.launch {
                stateUi.value = Loading
                when (val result = booksRepository.getChaptersId(bookId)) {
                    is Result.Success -> {
                        chapterIds.value = result.value
                        stateUi.value = Success(Unit)
                    }

                    is Result.Error -> stateUi.value = Error(result.error?.message ?: "")
                }
            }
        }

        fun deleteChapter() {

        }

        override fun onDestroy() {
            scope.cancel()
        }
    }
}