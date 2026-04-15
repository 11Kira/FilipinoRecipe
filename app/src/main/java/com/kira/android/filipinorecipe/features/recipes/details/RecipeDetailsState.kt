package com.kira.android.filipinorecipe.features.recipes.details

import com.kira.android.filipinorecipe.model.Recipe

sealed class RecipeDetailsState {
    data class ShowError(val error: Any) : RecipeDetailsState()
    data class SetRecipeDetails(val recipe: Recipe) : RecipeDetailsState()
}