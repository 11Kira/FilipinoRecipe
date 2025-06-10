package com.kira.android.filipinorecipe.features.recipes.create

import com.kira.android.filipinorecipe.features.recipes.Recipe

sealed class CreateRecipeState {
    data class ShowError(val error: Any) : CreateRecipeState()
    data class OnSuccessCreatedRecipe(val recipe: Recipe) : CreateRecipeState()
}