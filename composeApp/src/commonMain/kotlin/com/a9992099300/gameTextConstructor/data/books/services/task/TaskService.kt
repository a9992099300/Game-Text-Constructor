package com.a9992099300.gameTextConstructor.data.books.services.task

import com.a9992099300.gameTextConstructor.data.books.models.TaskDataModel
import io.ktor.client.statement.HttpResponse

interface TaskService{

     suspend fun addTask(
          userId: String,
          bookId: String,
          tasksDataModel: TaskDataModel
     ) : HttpResponse

     suspend fun getTasks(userId: String, bookId: String) : List<TaskDataModel>

     suspend fun getTaskIds(userId: String, bookId: String): List<String>

     suspend fun deleteTask(userId: String, bookId: String, taskId: String): HttpResponse
}