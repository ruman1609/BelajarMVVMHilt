package com.rudyrachman16.belajarmvvmhilt.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rudyrachman16.belajarmvvmhilt.BuildConfig

object ImageViewUtils {
    private fun Context.loadingDrawable() = CircularProgressDrawable(this).apply {
        strokeWidth = 5f
        centerRadius = 16f
        start()
    }

    fun ImageView.load(imageUrl: String, isIngredient: Boolean) {
        val url = if (isIngredient) (BuildConfig.INGREDIENT_URL + imageUrl) else imageUrl
        Glide.with(this).load(url)
            .apply(RequestOptions.placeholderOf(this.context.loadingDrawable())).into(this)
    }
}