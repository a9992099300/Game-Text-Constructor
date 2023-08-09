package com.a9992099300.gameTextConstructor.data.books.repository.task

import com.a9992099300.gameTextConstructor.data.books.models.TaskDataModel
import com.a9992099300.gameTextConstructor.data.books.services.task.TaskService
import com.a9992099300.gameTextConstructor.data.common.Result
import com.a9992099300.gameTextConstructor.data.common.SavedAuth
import com.a9992099300.gameTextConstructor.data.common.allowRequest
import com.a9992099300.gameTextConstructor.data.common.request
import com.a9992099300.gameTextConstructor.data.common.simpleRequest
import io.github.xxfast.kstore.KStore

class TaskRepositoryImpl(
    private val taskService: TaskService,
    private val store: KStore<SavedAuth>
) : TaskRepository {

    override suspend fun getTasks(
        bookId: String
    ): Result<List<TaskDataModel>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId?.let { taskService.getTasks(it,  bookId) }
    }

    override suspend fun addTask(bookId: String, model: TaskDataModel): Result<TaskDataModel>  = request {
            val userId = store.get()?.firstOrNull()?.localId
            userId?.let {
                taskService.addTask(
                    userId = it,
                    bookId = bookId,
                    tasksDataModel = model
                )
            }
        }

    override suspend fun getTaskIds(bookId: String): Result<List<String>> = simpleRequest {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest{
            taskService.getTaskIds(it, bookId)
        }
    }

    override suspend fun deleteTask(bookId: String, id: String): Result<Unit> = request {
        val userId = store.get()?.firstOrNull()?.localId
        userId.allowRequest{
            taskService.deleteTask(it, bookId, id)
        }
    }
}


