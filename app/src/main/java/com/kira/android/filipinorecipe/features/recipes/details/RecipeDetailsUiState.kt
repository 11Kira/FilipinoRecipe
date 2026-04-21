package com.kira.android.filipinorecipe.features.recipes.details

import com.kira.android.filipinorecipe.model.Recipe

data class RecipeDetailsUiState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)