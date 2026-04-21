package com.kira.android.filipinorecipe.features.recipes.favorites

import com.kira.android.filipinorecipe.model.Recipe

data class FavoriteRecipeListState(
    val recipes: List<Recipe>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)