package com.a9992099300.gameTextConstructor.data.books.repository.pages

import com.a9992099300.gameTextConstructor.data.books.models.PageDataModel
import com.a9992099300.gameTextConstructor.data.books.services.page.PageService
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import com.a9992099300.gameTextConstructor.data.common.allowRequest
import com.a9992099300.gameTextConstructor.data.common.request
import com.a9992099300.gameTextConstructor.data.common.simpleRequest
import io.github.xxfast.kstore.KStore

class PageRepositoryImpl(
    private val pageService: PageService,
    private val store: KStore<SavedAuth>
) : PagesRepository {

    override suspend fun getPages(
        bookId: String,
        chapterId: String,
        sceneId: String
    ): Result<List<PageDataModel>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId?.let { pageService.getPages(it, bookId, chapterId, sceneId) }
    }

    override suspend fun getPage(
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String
    ): Result<PageDataModel> = request {
        val userId = store.get()?.firstOrNull()?.localId
        userId?.let { pageService.getPage(it, bookId, chapterId, sceneId, pageId) }
    }

    override suspend fun addPage(model: PageDataModel): Result<PageDataModel> = request {
        val userId = store.get()?.firstOrNull()?.localId
        val token = store.get()?.firstOrNull()?.idToken ?: ""
        userId?.let {
            pageService.addPage(
                userId = it,
                model = model,
                token = token
            )
        }
    }


    override suspend fun getPagesIds(
        bookId: String,
        chapterId: String,
        sceneId: String
    ): Result<List<String>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest {
            pageService.getPageIds(it, bookId, chapterId, sceneId)
        }
    }

    override suspend fun deletePage(
        bookId: String,
        chapterId: String,
        sceneId: String,
        pageId: String
    ): Result<Unit> = request{
        val userId = store.get()?.firstOrNull()?.localId
        val token = store.get()?.firstOrNull()?.idToken ?: ""
        userId.allowRequest{
            pageService.deletePage(it, bookId, chapterId, sceneId, pageId, token)
        }
    }
}


