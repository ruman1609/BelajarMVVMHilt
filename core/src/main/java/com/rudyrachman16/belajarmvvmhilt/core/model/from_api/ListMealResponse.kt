package com.rudyrachman16.belajarmvvmhilt.core.model.from_api

import com.google.gson.annotations.SerializedName

data class ListMealResponse(

	@field:SerializedName("meals")
	val meals: List<MealResponse>?
)
