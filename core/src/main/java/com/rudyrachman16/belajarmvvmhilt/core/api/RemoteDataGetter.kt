package com.rudyrachman16.belajarmvvmhilt.core.api

import android.util.Log
import com.rudyrachman16.belajarmvvmhilt.core.model.from_api.MealResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

class RemoteDataGetter @Inject constructor(private val mealService: MealService) {
    suspend fun getCategories(): Flow<MealResponseStatus<List<MealResponse>>> = flow {
        try {
            val data = mealService.getCategories().meals ?: listOf()
            if (data.isEmpty()) {
                emit(MealResponseStatus.Empty)
            } else {
                emit(MealResponseStatus.Success(data))
            }
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            } else {
                emit(MealResponseStatus.Failed(e.toString()))
            }
            Log.e("RetrofitGetData", e.toString())
        }
    }.catch {
        emit(MealResponseStatus.Failed(it.toString()))
    }.flowOn(Dispatchers.IO)

    suspend fun getSearch(query: String) = mealService.getSearch(query)

    suspend fun getDetail(id: String): Flow<MealResponseStatus<MealResponse>> = flow {
        try {
            val meal = mealService.getDetail(id).meals!![0]
            emit(MealResponseStatus.Success(meal))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            } else {
                emit(MealResponseStatus.Failed(e.toString()))
            }
            Log.e("RetrofitGetData", e.toString())
        }
    }.catch {
        emit(MealResponseStatus.Failed(it.toString()))
    }.flowOn(Dispatchers.IO)
}