package com.rudyrachman16.belajarmvvmhilt.ui.recommended

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rudyrachman16.belajarmvvmhilt.databinding.ItemMealBinding
import com.rudyrachman16.belajarmvvmhilt.utils.ImageViewUtils.load
import com.rudyrachman16.belajarmvvmhilt.core.model.domain.Meal

class RecommendedAdapter(private val clickCallback: (idMeal: String) -> Unit) :
    ListAdapter<Meal, RecommendedAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean =
            oldItem.idMeal == newItem.idMeal

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean =
            oldItem == newItem

    }) {

    inner class ViewHolder(private val bind: ItemMealBinding) : RecyclerView.ViewHolder(bind.root) {
        fun binding(meal: Meal) {
            bind.ivMeal.load(meal.strMealThumb, false)
            bind.tvMeal.text = meal.strMeal

            bind.root.setOnClickListener {
                clickCallback(meal.idMeal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(getItem(position))
    }
}