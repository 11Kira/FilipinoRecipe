package com.kira.android.filipinorecipe.features.recipes

import com.google.gson.JsonObject
import com.kira.android.filipinorecipe.features.recipes.model.Recipe
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeRemoteSource: RecipeRemoteSource
) {
    suspend fun getAllRecipes(): List<Recipe> {
        return recipeRemoteSource.getAllRecipes()
    }

    suspend fun getRecipeById(recipeId: String): Recipe {
        return recipeRemoteSource.getRecipeById(recipeId = recipeId)
    }

    suspend fun saveRecipe(body: JsonObject): Recipe {
        return recipeRemoteSource.saveRecipe(body)
    }

    suspend fun updateRecipeById(recipeId: String, body: JsonObject): Recipe {
        return recipeRemoteSource.updateRecipeById(recipeId = recipeId, body = body)
    }

    suspend fun deleteRecipeById(recipeId: String) {
        return recipeRemoteSource.deleteRecipeById(recipeId = recipeId)
    }
}