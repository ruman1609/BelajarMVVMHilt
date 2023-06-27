package com.rudyrachman16.belajarmvvmhilt.utils

import android.view.View
import android.widget.Toast

object ActivityViewUtils {
    fun View.showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}