package com.rudyrachman16.belajarmvvmhilt.utils

import android.content.Context

object GridBoxCountUtils {
    private const val MARGIN_SPACE = 4
    private const val WIDTH = 150
    fun Context.countBox(): Int {
        val display = resources.displayMetrics
        val screenWidth: Float = display.widthPixels / display.density
        return (screenWidth / (WIDTH + MARGIN_SPACE * 2) + 0.5).toInt()
    }
}