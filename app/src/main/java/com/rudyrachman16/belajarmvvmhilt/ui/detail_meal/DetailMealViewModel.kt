package com.rudyrachman16.belajarmvvmhilt.ui.detail_meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rudyrachman16.belajarmvvmhilt.core.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMealViewModel @Inject constructor(private val repository: MealRepository): ViewModel() {
    fun getData(id: String) = repository.getDetail(id).asLiveData()
}