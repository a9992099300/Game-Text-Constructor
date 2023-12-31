package com.a9992099300.gameTextConstructor.logic.constructor.book

import com.a9992099300.gameTextConstructor.data.books.repository.book.BooksRepository
import com.a9992099300.gameTextConstructor.data.books.repository.chapter.ChaptersRepository
import com.a9992099300.gameTextConstructor.data.books.repository.pages.PagesRepository
import com.a9992099300.gameTextConstructor.data.books.repository.scenes.ScenesRepository
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.di.Inject
import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.ui.screen.models.ChapterUIModel
import com.a9992099300.gameTextConstructor.ui.screen.models.PageUIModel
import com.a9992099300.gameTextConstructor.ui.screen.models.SceneUIModel
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

class BookConstructorComponentImpl(
    componentContext: ComponentContext,
    private val bookId: String,
    private val popBack: () -> (Unit),
    private val onCreateChapter: () -> (Unit),
    private val onEditChapter: (String) -> (Unit),
    private val onCreateScene: (String) -> (Unit),
    private val onEditScene: (String, String) -> (Unit),
    private val onOpenInventory: () -> (Unit),
    private val onCreateOrEditPage: (String, String, String) -> Unit,
    private val booksRepository: BooksRepository = Inject.instance(),
    private val chapterRepository: ChaptersRepository = Inject.instance(),
    private val scenesRepository: ScenesRepository = Inject.instance(),
    private val pagesRepository: PagesRepository = Inject.instance()
) : BookConstructorComponent, ComponentContext by componentContext {

    override val title: MutableStateFlow<String> = MutableStateFlow(bookId)

    override val chapters: MutableStateFlow<StateUi<List<ChapterUIModel>>> =
        MutableStateFlow(StateUi.Initial)

    override val scenes: MutableStateFlow<StateUi<List<SceneUIModel>>> =
        MutableStateFlow(StateUi.Initial)

    override val pages: MutableStateFlow<StateUi<List<PageUIModel>>> =
        MutableStateFlow(StateUi.Initial)

    override val chapterHide: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    override val scenesHide: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    override fun loadChapters() {
        bookRetainedInstance.loadChapters()
    }

    override fun loadScenes(chapterId: String) {
        selectChapter(chapterId)
        bookRetainedInstance.loadScenes(chapterId)
    }

    override fun loadPages(sceneId: String) {
        selectScene(sceneId)
        val selectedChapter = (chapters.value as? StateUi.Success)?.value?.find { it.selected }
        selectedChapter?.let { chapter ->
            bookRetainedInstance.loadPages(
                chapter.chapterId,
                sceneId
            )
        }
    }

    override fun hideChapters(value: Boolean) {
        chapterHide.value = value
    }

    override fun hideScenes(value: Boolean) {
        scenesHide.value = value
    }

    override fun popBack() {
        this.popBack.invoke()
    }

    override fun onCreateChapter() {
        this.onCreateChapter.invoke()
    }

    override fun onEditChapter(chapter: ChapterUIModel) {
        this.onEditChapter.invoke(chapter.chapterId)
    }

    override fun onCreateScene() {
        val currentState = chapters.value
        if (currentState is StateUi.Success) {
            currentState.value.find { it.selected }?.chapterId?.let { this.onCreateScene(it) }
        } else {
            chapters.value = StateUi.Error(messageError = "Выберите главу чтобы создать сцену")
        }
    }

    override fun onCreatePage() {
        val currentState = scenes.value
        if (currentState is StateUi.Success) {
            currentState.value.find { it.selected }?.let {
                this.onCreateOrEditPage(
                it.chapterId,
                it.sceneId,
                ""
            ) }
        } else {
            chapters.value = StateUi.Error(messageError = "Выберите сцену чтобы создать страницу")
        }
    }

    override fun onEditPage(pageId: String) {
        val currentState = scenes.value
        if (currentState is StateUi.Success) {
            currentState.value.find { it.selected }?.let {
                this.onCreateOrEditPage(
                    it.chapterId,
                    it.sceneId,
                    pageId
                ) }
        } else {
            chapters.value = StateUi.Error(messageError = "Выберите сцену чтобы создать страницу")
        }
    }

    override fun onEditScene(scene: SceneUIModel) {
        this.onEditScene.invoke(scene.chapterId, scene.sceneId)
    }

    override fun refresh() {
        bookRetainedInstance.refresh()
    }

    override fun openInventory() {
        this.onOpenInventory()
    }

    private val bookRetainedInstance =
        instanceKeeper.getOrCreate { BookRetainedInstance(Dispatchers.Default) }

    inner class BookRetainedInstance(mainContext: CoroutineContext) :
        InstanceKeeper.Instance {

        private val scope = CoroutineScope(mainContext + SupervisorJob())

        init {
            loadChapters()
        }

       fun refresh() {
           loadChapters()
           scope.launch {
               scenes.emit(StateUi.Initial)
               pages.emit(StateUi.Initial)
           }
        }

        fun loadChapters() {
            chapters.value = StateUi.Loading
            scope.launch {
                val result = chapterRepository.getChapters(bookId)
                when (result) {
                    is Result.Success -> chapters.value = StateUi.Success(
                        result.value.map {
                            it.mapToUI()
                        }
                    )

                    is Result.Error -> chapters.value = StateUi.Error(
                        result.error?.message ?: "Error"
                    )
                }
            }
        }

        fun loadScenes(chapterId: String) {
            scenes.value = StateUi.Loading
            scope.launch {
                val result = scenesRepository.getScenes(bookId, chapterId)
                when (result) {
                    is Result.Success -> scenes.value = StateUi.Success(
                        result.value.map {
                            it.mapToUI()
                        }
                    )

                    is Result.Error -> scenes.value = StateUi.Error(
                        result.error?.message ?: "Error"
                    )
                }
            }
        }

        fun loadPages(chapterId: String, sceneId: String) {
            pages.value = StateUi.Loading
            scope.launch {
                val result = pagesRepository.getPages(bookId, chapterId, sceneId)
                when (result) {
                    is Result.Success -> pages.value = StateUi.Success(
                        result.value.map {
                            it.mapToUI()
                        }
                    )

                    is Result.Error -> pages.value = StateUi.Error(
                        result.error?.message ?: "Error"
                    )
                }
            }
        }

        override fun onDestroy() {
            scope.cancel()
        }
    }

    private fun selectChapter(chapterId: String) {
        val listChapters =
            (chapters.value as? StateUi.Success)?.value?.map { model ->
                model.copy(selected = model.chapterId == chapterId)
            }
        listChapters?.let {
            chapters.value = StateUi.Success(it)
        }
    }

    private fun selectScene(sceneId: String) {
        val listScenes =
            (scenes.value as? StateUi.Success)?.value?.map { model ->
                model.copy(selected = model.sceneId == sceneId)
            }
        listScenes?.let { scenes.value = StateUi.Success(it) }
    }

}