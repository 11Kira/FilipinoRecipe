package com.kira.android.filipinorecipe.features.recipes.favorites

sealed class FavoriteRecipeListState {
    data class ShowError(val error: Any) : FavoriteRecipeListState()
}