package com.rudyrachman16.belajarmvvmhilt.ui.detail_meal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rudyrachman16.belajarmvvmhilt.databinding.ItemIngredientsBinding
import com.rudyrachman16.belajarmvvmhilt.utils.ImageViewUtils.load

class DetailMealIngredientAdapter(
    private val measurements: List<String>,
    private val ingredients: List<String>
) :
    RecyclerView.Adapter<DetailMealIngredientAdapter.ViewHolder>() {
    inner class ViewHolder(private val bind: ItemIngredientsBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun binding(ingredient: String, measurement: String) {
            bind.ingredient.text = ingredient
            bind.measurement.text = measurement

            bind.ivIngredientThumb.load("$ingredient-Small.png", true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemIngredientsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(ingredients[holder.adapterPosition], measurements[holder.adapterPosition])
    }
}