package com.a9992099300.gameTextConstructor.data.books.repository.scenes

import com.a9992099300.gameTextConstructor.data.books.models.SceneDataModel
import com.a9992099300.gameTextConstructor.data.common.Result

interface ScenesRepository {

    suspend fun getScenes(bookId: String, chapterId: String) : Result<List<SceneDataModel>>

    suspend fun addScene(model: SceneDataModel) : Result<SceneDataModel>

    suspend fun getScene(bookId: String, chapterId: String, sceneId: String) : Result<SceneDataModel>

    suspend fun getSceneIds(bookId: String, chapterId: String): Result<List<String>>

    suspend fun deleteScene(bookId: String, chapterId: String, scendeId: String): Result<Unit>

}