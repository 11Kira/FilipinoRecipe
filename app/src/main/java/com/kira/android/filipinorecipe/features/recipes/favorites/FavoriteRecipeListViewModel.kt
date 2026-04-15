package com.kira.android.filipinorecipe.features.recipes.favorites

import androidx.lifecycle.ViewModel
import com.kira.android.filipinorecipe.features.recipes.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipeListViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
) : ViewModel()