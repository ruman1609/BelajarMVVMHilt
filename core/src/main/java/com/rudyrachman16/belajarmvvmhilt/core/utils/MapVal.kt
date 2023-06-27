package com.rudyrachman16.belajarmvvmhilt.core.utils

import com.rudyrachman16.belajarmvvmhilt.core.model.domain.Meal
import com.rudyrachman16.belajarmvvmhilt.core.model.from_api.MealResponse

object MapVal {
    private fun check(str: String?): String = if (str != null && str != "") ",$str" else ""

    fun mealResToDom(meal: MealResponse): Meal {
        val ingredientsString =
            "${meal.strIngredient1 ?: ""}${check(meal.strIngredient2)}${check(meal.strIngredient3)}" +
                    "${check(meal.strIngredient4)}${check(meal.strIngredient5)}${check(meal.strIngredient6)}" +
                    "${check(meal.strIngredient7)}${check(meal.strIngredient8)}${check(meal.strIngredient9)}" +
                    "${check(meal.strIngredient10)}${check(meal.strIngredient11)}${check(meal.strIngredient12)}" +
                    "${check(meal.strIngredient13)}${check(meal.strIngredient14)}${check(meal.strIngredient15)}" +
                    "${check(meal.strIngredient16)}${check(meal.strIngredient17)}${check(meal.strIngredient18)}" +
                    "${check(meal.strIngredient19)}${check(meal.strIngredient20)}"
        val ingredients: List<String> = ingredientsString.split(",").toList()

        val measurementsString =
            "${meal.strMeasure1 ?: ""}${check(meal.strMeasure2)}${check(meal.strMeasure3)}" +
                    "${check(meal.strMeasure4)}${check(meal.strMeasure5)}${check(meal.strMeasure6)}" +
                    "${check(meal.strMeasure7)}${check(meal.strMeasure8)}${check(meal.strMeasure9)}" +
                    "${check(meal.strMeasure10)}${check(meal.strMeasure11)}${check(meal.strMeasure12)}" +
                    "${check(meal.strMeasure13)}${check(meal.strMeasure14)}${check(meal.strMeasure15)}" +
                    "${check(meal.strMeasure16)}${check(meal.strMeasure17)}${check(meal.strMeasure18)}" +
                    "${check(meal.strMeasure19)}${check(meal.strMeasure20)}"
        val measurements: List<String> = measurementsString.split(",").toList()

        return Meal(
            meal.idMeal ?: "", meal.strMeal ?: "", meal.strMealThumb ?: "",
            meal.strCategory ?: CategorySetter.getCategory(), meal.strArea,
            meal.strInstructions, meal.strDrinkAlternate, meal.strYoutube, meal.strSource,
            ingredients, measurements
        )
    }
}