package com.a9992099300.gameTextConstructor.logic.constructor.createScenes

import com.a9992099300.gameTextConstructor.logic.base.BaseComponent
import com.a9992099300.gameTextConstructor.ui.screen.models.SceneUIModel
import kotlinx.coroutines.flow.StateFlow

interface CreateOrEditScenesComponent: BaseComponent<Unit> {

    val sceneState: StateFlow<SceneUIModel>

    val editeSceneModel: String

    val scenesIds: StateFlow<List<String>>

    val snackBar: StateFlow<String>

    fun changeTitle(title: String)

    fun changeNumber(title: String)

    fun changeDescription(description: String)

    fun addOrEditScene()

    fun resetError()

    fun deleteScene()

}