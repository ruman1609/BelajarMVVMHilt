package com.rudyrachman16.belajarmvvmhilt.core.repository

sealed class Status<out T> {
    data class Success<out T>(val data: T): Status<T>()
    data class Error<Nothing>(val errMsg: String): Status<Nothing>()
    object Loading: Status<Nothing>()
}
