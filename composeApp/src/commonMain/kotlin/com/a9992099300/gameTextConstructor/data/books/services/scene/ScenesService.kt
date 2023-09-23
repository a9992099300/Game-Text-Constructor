package com.a9992099300.gameTextConstructor.data.books.services.scene

import com.a9992099300.gameTextConstructor.data.books.models.SceneDataModel
import io.ktor.client.statement.HttpResponse

interface ScenesService{

     suspend fun addScene(
          userId: String,
          model: SceneDataModel,
          token: String
     ) : HttpResponse

     suspend fun getScenes(userId: String, bookId: String, chapterId: String) : List<SceneDataModel>

     suspend fun getScene(userId: String, bookId: String, chapterId: String, sceneId: String) : HttpResponse

     suspend fun getSceneIds(userId: String, bookId: String, chapterId: String): List<String>

     suspend fun deleteScene(userId: String, bookId: String, chapterId: String, sceneId: String,
                             token: String): HttpResponse
}