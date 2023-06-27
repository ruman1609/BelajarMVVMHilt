package com.rudyrachman16.belajarmvvmhilt.core.api

sealed class MealResponseStatus<out T> {
    data class Success<out T>(val data: T): MealResponseStatus<T>()
    object Empty: MealResponseStatus<Nothing>()
    data class Failed(val errorMsg: String): MealResponseStatus<Nothing>()
}
