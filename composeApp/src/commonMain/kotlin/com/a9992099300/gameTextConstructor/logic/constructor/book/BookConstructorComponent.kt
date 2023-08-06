package com.a9992099300.gameTextConstructor.logic.constructor.book

import com.a9992099300.gameTextConstructor.logic.common.StateUi
import com.a9992099300.gameTextConstructor.ui.screen.models.ChapterUIModel
import com.a9992099300.gameTextConstructor.ui.screen.models.PageUIModel
import com.a9992099300.gameTextConstructor.ui.screen.models.SceneUIModel
import kotlinx.coroutines.flow.StateFlow

interface BookConstructorComponent {

    val title: StateFlow<String>

    val chapters: StateFlow<StateUi<List<ChapterUIModel>>>

    val scenes: StateFlow<StateUi<List<SceneUIModel>>>

    val pages: StateFlow<StateUi<List<PageUIModel>>>

    val chapterHide: StateFlow<Boolean>

    val scenesHide: StateFlow<Boolean>

   fun loadChapters()

    fun loadScenes(chapterId: String)

    fun loadPages(sceneId: String)

    fun hideChapters(value: Boolean)

    fun hideScenes(value: Boolean)

    fun popBack()

    fun onCreateChapter()

    fun onEditChapter(chapter: ChapterUIModel)

    fun onCreateScene()

    fun onEditScene(scene: SceneUIModel)
}