package com.kira.android.filipinorecipe.features.recipes.list

sealed class RecipeListState {
    data class ShowError(val error: Any) : RecipeListState()
}