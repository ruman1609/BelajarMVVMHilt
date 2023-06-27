package com.rudyrachman16.belajarmvvmhilt.core.repository

import android.util.Log
import com.rudyrachman16.belajarmvvmhilt.core.api.MealResponseStatus
import com.rudyrachman16.belajarmvvmhilt.core.api.RemoteDataGetter
import com.rudyrachman16.belajarmvvmhilt.core.model.domain.Meal
import com.rudyrachman16.belajarmvvmhilt.core.utils.CategorySetter
import com.rudyrachman16.belajarmvvmhilt.core.utils.MapVal
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepository @Inject constructor(private val remoteDataGetter: RemoteDataGetter) {
    fun getRecommended(): Flow<Status<List<Meal>>> = flow {
        emit(Status.Loading)
        when (val data = remoteDataGetter.getCategories().firstOrNull()) {
            is MealResponseStatus.Success -> {
                emit(Status.Success(data.data.map { MapVal.mealResToDom(it) }))
            }

            is MealResponseStatus.Failed -> {
                Log.e("ErrorGetRecommended", data.errorMsg)
                emit(Status.Error(data.errorMsg))
            }

            is MealResponseStatus.Empty -> emit(Status.Success(listOf()))
            else -> emit(Status.Success(listOf()))
        }
    }.flowOn(Dispatchers.IO)

    fun getDetail(id: String): Flow<Status<Meal>> = flow {
        emit(Status.Loading)
        when (val data = remoteDataGetter.getDetail(id).first()) {
            is MealResponseStatus.Failed -> emit(Status.Error(data.errorMsg))
            is MealResponseStatus.Success -> emit(Status.Success(MapVal.mealResToDom(data.data)))
            is MealResponseStatus.Empty -> emit(Status.Error("Kosong"))
        }
    }.flowOn(Dispatchers.IO)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun search(state: StateFlow<String>): Flow<List<Meal>> =
        state.debounce(300).distinctUntilChanged().mapLatest {
            remoteDataGetter.getSearch(it).meals!!.map { result -> MapVal.mealResToDom(result) }
        }.flowOn(Dispatchers.IO)
}