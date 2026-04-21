package com.kira.android.filipinorecipe.features.recipes.list

import com.kira.android.filipinorecipe.model.Recipe

data class RecipeListState(
    val recipes: List<Recipe>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)