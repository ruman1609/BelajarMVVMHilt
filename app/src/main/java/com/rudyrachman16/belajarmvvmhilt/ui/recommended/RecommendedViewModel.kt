package com.rudyrachman16.belajarmvvmhilt.ui.recommended

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rudyrachman16.belajarmvvmhilt.core.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendedViewModel @Inject constructor(repository: MealRepository): ViewModel() {
    val recommended = repository.getRecommended().asLiveData()
}