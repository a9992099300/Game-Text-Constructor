package com.a9992099300.gameTextConstructor.data.books.repository.chapter

import com.a9992099300.gameTextConstructor.data.books.models.ChapterDataModel
import com.a9992099300.gameTextConstructor.data.books.services.chapter.ChapterService
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import com.a9992099300.gameTextConstructor.data.common.allowRequest
import com.a9992099300.gameTextConstructor.data.common.request
import com.a9992099300.gameTextConstructor.data.common.simpleRequest
import io.github.xxfast.kstore.KStore

class ChapterRepositoryImpl(
    private val chapterService: ChapterService,
    private val store: KStore<SavedAuth>
) : ChaptersRepository {

    override suspend fun addChapter(model: ChapterDataModel) : Result<ChapterDataModel> = request {
        val userId = store.get()?.firstOrNull()?.localId
        val token = store.get()?.firstOrNull()?.idToken
        userId?.let {
            chapterService.addChapter(it, model.bookId, model,token ?: "")
        }
    }

    override suspend fun getChapters(bookId: String): Result<List<ChapterDataModel>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId?.let { chapterService.getChapters(it, bookId) }
    }

    override suspend fun getChapter(bookId: String, chapterId: String): Result<ChapterDataModel>  = request {
        val userId = store.get()?.firstOrNull()?.localId
        userId?.let { chapterService.getChapter(it, bookId, chapterId) }
    }

    override suspend fun getChaptersId(bookId: String): Result<List<String>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest{
            chapterService.getChaptersId(it, bookId)
        }
    }

    override suspend fun deleteChapter(bookId: String, chapterId: String): Result<Unit> = request{
        val userId = store.get()?.firstOrNull()?.localId
        val token = store.get()?.firstOrNull()?.idToken ?: ""

        userId.allowRequest{
            chapterService.deleteChapter(it, bookId, chapterId, token)
        }
    }
}


