package com.kira.android.filipinorecipe.features.recipes.list

import com.kira.android.filipinorecipe.features.recipes.Recipe

sealed class RecipeListState {
    data class ShowError(val error: Any) : RecipeListState()
    data class SetRecipeList(val notes: List<Recipe>) : RecipeListState()
}