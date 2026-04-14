package com.kira.android.filipinorecipe.features.recipes.favorites

import com.kira.android.filipinorecipe.model.Recipe

sealed class FavoriteRecipeListState {
    data class ShowError(val error: Any) : FavoriteRecipeListState()
    data class SetFavoriteRecipeList(val recipeList: List<Recipe>) : FavoriteRecipeListState()
}