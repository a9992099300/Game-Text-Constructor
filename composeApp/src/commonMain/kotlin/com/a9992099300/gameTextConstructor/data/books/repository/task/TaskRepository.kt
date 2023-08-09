package com.a9992099300.gameTextConstructor.data.books.repository.task

import com.a9992099300.gameTextConstructor.data.books.models.TaskDataModel
import com.a9992099300.gameTextConstructor.data.common.Result

interface TaskRepository {

    suspend fun getTasks(bookId: String) : Result<List<TaskDataModel>>

    suspend fun addTask(bookId: String, model: TaskDataModel) : Result<TaskDataModel>

    suspend fun getTaskIds(bookId: String): Result<List<String>>

    suspend fun deleteTask(bookId: String, id: String): Result<Unit>

}