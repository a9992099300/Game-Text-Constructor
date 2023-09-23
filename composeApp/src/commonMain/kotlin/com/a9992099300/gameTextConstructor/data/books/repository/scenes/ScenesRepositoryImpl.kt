package com.a9992099300.gameTextConstructor.data.books.repository.scenes

import com.a9992099300.gameTextConstructor.data.books.models.SceneDataModel
import com.a9992099300.gameTextConstructor.data.books.services.scene.ScenesService
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import com.a9992099300.gameTextConstructor.data.common.allowRequest
import com.a9992099300.gameTextConstructor.data.common.request
import com.a9992099300.gameTextConstructor.data.common.simpleRequest
import io.github.xxfast.kstore.KStore

class ScenesRepositoryImpl(
    private val scenesService: ScenesService,
    private val store: KStore<SavedAuth>
) : ScenesRepository {

    override suspend fun getScenes(
        bookId: String,
        chapterId: String
    ): Result<List<SceneDataModel>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId?.let { scenesService.getScenes(it,  bookId, chapterId) }
    }

    override suspend fun addScene(model: SceneDataModel): Result<SceneDataModel>  = request {
            val userId = store.get()?.firstOrNull()?.localId
            val token = store.get()?.firstOrNull()?.idToken ?: ""
            userId?.let {
                scenesService.addScene(
                    userId = it,
                    model = model,
                    token = token
                )
            }
        }

    override suspend fun getScene(
        bookId: String,
        chapterId: String,
        sceneId: String
    ): Result<SceneDataModel> = request {
        val userId = store.get()?.firstOrNull()?.localId
        userId?.let { scenesService.getScene(it,  bookId, chapterId, sceneId) }
    }

    override suspend fun getSceneIds(bookId: String, chapterId: String): Result<List<String>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest{
            scenesService.getSceneIds(it, bookId, chapterId)
        }
    }

    override suspend fun deleteScene(bookId: String, chapterId: String, scendeId: String): Result<Unit> = request{
        val userId = store.get()?.firstOrNull()?.localId
        val token = store.get()?.firstOrNull()?.idToken ?: ""
        userId.allowRequest{
            scenesService.deleteScene(it, bookId, chapterId, scendeId, token)
        }
    }
}


