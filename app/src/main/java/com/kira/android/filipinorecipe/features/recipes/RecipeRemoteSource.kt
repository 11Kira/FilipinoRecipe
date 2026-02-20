package com.kira.android.filipinorecipe.features.recipes

import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRemoteSource @Inject constructor(
    private val recipeService: RecipeService
) {
    suspend fun getAllRecipes(query: String, page: Int) =
        withContext(Dispatchers.IO) { recipeService.getAllRecipes(query, page) }

    suspend fun getRecipeById(recipeId: String) =
        withContext(Dispatchers.IO) { recipeService.getRecipeById(recipeId) }

    suspend fun deleteRecipeById(recipeId: String) =
        withContext(Dispatchers.IO) { recipeService.deleteRecipeById(recipeId) }

    suspend fun saveRecipe(body: JsonObject) =
        withContext(Dispatchers.IO) { recipeService.saveRecipe(body) }

    suspend fun updateRecipeById(recipeId: String, body: JsonObject) =
        withContext(Dispatchers.IO) { recipeService.updateRecipe(recipeId, body) }
}