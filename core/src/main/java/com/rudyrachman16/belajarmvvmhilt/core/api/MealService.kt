package com.rudyrachman16.belajarmvvmhilt.core.api

import com.rudyrachman16.belajarmvvmhilt.core.model.from_api.ListMealResponse
import com.rudyrachman16.belajarmvvmhilt.core.model.from_api.MealResponse
import com.rudyrachman16.belajarmvvmhilt.core.utils.CategorySetter
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {
    @GET("filter.php")
    suspend fun getCategories(
        @Query("c") category: String = CategorySetter.getCategory()
    ): ListMealResponse

    @GET("search.php")
    suspend fun getSearch(@Query("s") search: String): ListMealResponse

    @GET("lookup.php")
    suspend fun getDetail(@Query("i") search: String): ListMealResponse
}