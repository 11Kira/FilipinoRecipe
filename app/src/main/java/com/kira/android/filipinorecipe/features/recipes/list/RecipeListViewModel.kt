package com.kira.android.filipinorecipe.features.recipes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kira.android.filipinorecipe.features.recipes.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
) : ViewModel() {

    val recipePagingFlow = recipeUseCase.getAllRecipes().cachedIn(viewModelScope)
}