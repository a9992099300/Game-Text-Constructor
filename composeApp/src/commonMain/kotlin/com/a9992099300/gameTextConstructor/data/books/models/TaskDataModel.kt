package com.a9992099300.gameTextConstructor.data.books.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskDataModel(
    @SerialName("bookId") val bookId: String,
    @SerialName("taskId") val taskId: String,
    @SerialName("title")  val title: String,
    @SerialName("description")  val description: String,
    @SerialName("status")  val statusTask: String,
)

@Serializable
enum class StatusTask(value: String){
    NOT_TAKEN("not_taken"),
    TAKEN("taken"),
    FINISHED("finished"),
    FAILED("failed")
}