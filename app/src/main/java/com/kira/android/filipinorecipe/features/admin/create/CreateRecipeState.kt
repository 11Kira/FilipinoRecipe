package com.kira.android.filipinorecipe.features.admin.create

import com.kira.android.filipinorecipe.features.recipes.model.Recipe

sealed class CreateRecipeState {
    data class ShowError(val error: Any) : CreateRecipeState()
    data class OnSuccessCreatedRecipe(val recipe: Recipe) : CreateRecipeState()
}