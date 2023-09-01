package com.a9992099300.gameTextConstructor.data.books.repository.actions

import com.a9992099300.gameTextConstructor.data.books.models.ItemPage
import com.a9992099300.gameTextConstructor.data.books.services.actions.ActionService
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import com.a9992099300.gameTextConstructor.data.common.allowRequest
import com.a9992099300.gameTextConstructor.data.common.request
import com.a9992099300.gameTextConstructor.data.common.simpleRequest
import io.github.xxfast.kstore.KStore

class ActionRepositoryImpl(
    private val actionService: ActionService,
    private val store: KStore<SavedAuth>
) : ActionRepository {


    override suspend fun getActions(
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String
    ): Result<List<ItemPage>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId?.let { actionService.getActions(it, bookId, chapterId, sceneId, pageId) }
    }

    override suspend fun getAction(
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String,
        actionId: String
    ): Result<ItemPage> = request {
        val userId = store.get()?.firstOrNull()?.localId
        userId?.let { actionService.getAction(it, bookId, chapterId, sceneId, pageId, actionId) }
    }

    override suspend fun addAction(
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String,
        model: ItemPage
    ): Result<ItemPage> = request {
        val userId = store.get()?.firstOrNull()?.localId
        val token = store.get()?.firstOrNull()?.idToken ?: ""
        userId?.let {
            actionService.addAction(
                userId = it,
                bookId = bookId,
                chapterId = chapterId,
                sceneId = sceneId,
                pageId = pageId,
                model = model,
                token = token
            )
        }
    }

    override suspend fun getActionIds(
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String
    ): Result<List<String>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest {
            actionService.getActionIds(it, bookId, chapterId, sceneId, pageId)
        }
    }

    override suspend fun deleteAction(
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String,
        actionId: String
    ): Result<Unit> = request {
        val userId = store.get()?.firstOrNull()?.localId
        val token = store.get()?.firstOrNull()?.idToken ?: ""
        userId.allowRequest {
            actionService.deleteAction(it, bookId, chapterId, sceneId, pageId, actionId, token)
        }
    }
}


