package com.kira.android.filipinorecipe.features.recipes.details

import com.kira.android.filipinorecipe.features.recipes.Recipe

sealed class RecipeState {
    data class ShowError(val error: Any) : RecipeState()
    data class SetRecipeDetails(val recipe: Recipe) : RecipeState()

}