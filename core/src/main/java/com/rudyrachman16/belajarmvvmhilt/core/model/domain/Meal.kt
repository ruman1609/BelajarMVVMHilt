package com.rudyrachman16.belajarmvvmhilt.core.model.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String? = null,
    val strArea: String? = null,
    val strInstructions: String? = null,
    val strDrinkAlternate: String? = null,
    val strYoutube: String? = null,
    val strSource: String? = null,
    val ingredients: List<String>? = null,
    val measurements: List<String>? = null
) : Parcelable