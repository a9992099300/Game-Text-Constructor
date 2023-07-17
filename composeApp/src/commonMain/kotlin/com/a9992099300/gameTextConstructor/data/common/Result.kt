package com.a9992099300.gameTextConstructor.data.common

sealed class Result<out T> {

    data class Success<out T>(val value: T) : Result<T>()

    open class Error(val error: Throwable? = null) : Result<Nothing>()

}