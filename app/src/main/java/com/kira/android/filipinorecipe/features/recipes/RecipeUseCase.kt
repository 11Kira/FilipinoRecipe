package com.kira.android.filipinorecipe.features.recipes

import com.google.gson.JsonObject
import javax.inject.Inject

class RecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
){
    suspend fun getAllRecipes(): List<Recipe> {
        return recipeRepository.getAllRecipes()
    }

    suspend fun getRecipeById(recipeId: String): Recipe {
        return recipeRepository.getRecipeById(recipeId = recipeId)
    }

    suspend fun savRecipe(body: JsonObject): Recipe {
        return recipeRepository.saveRecipe(body)
    }

    suspend fun updateRecipeById(recipeId: String, body: JsonObject): Recipe {
        return recipeRepository.updateRecipeById(recipeId = recipeId, body = body)
    }

    suspend fun deleteRecipeById(recipeId: String) {
        return recipeRepository.deleteRecipeById(recipeId = recipeId)
    }
}