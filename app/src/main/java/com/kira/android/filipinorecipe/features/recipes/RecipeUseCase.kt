package com.kira.android.filipinorecipe.features.recipes

import androidx.paging.PagingData
import com.google.gson.JsonObject
import com.kira.android.filipinorecipe.model.Recipe
import com.kira.android.filipinorecipe.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
){
    fun getAllRecipes(query: String): Flow<PagingData<Recipe>> =
        recipeRepository.getAllRecipes(query)

    suspend fun getRecipeById(recipeId: String): ApiResponse<Recipe> {
        return recipeRepository.getRecipeById(recipeId = recipeId)
    }

    suspend fun saveRecipe(body: JsonObject): ApiResponse<Recipe> {
        return recipeRepository.saveRecipe(body)
    }

    suspend fun updateRecipeById(recipeId: String, body: JsonObject): ApiResponse<Recipe> {
        return recipeRepository.updateRecipeById(recipeId = recipeId, body = body)
    }

    suspend fun deleteRecipeById(recipeId: String) {
        return recipeRepository.deleteRecipeById(recipeId = recipeId)
    }
}