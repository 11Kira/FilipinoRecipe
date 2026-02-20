package com.kira.android.filipinorecipe.features.recipes

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.JsonObject
import com.kira.android.filipinorecipe.features.recipes.list.RecipePagingSource
import com.kira.android.filipinorecipe.model.Recipe
import com.kira.android.filipinorecipe.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeRemoteSource: RecipeRemoteSource
) {
    fun getAllRecipes(query: String): Flow<PagingData<Recipe>> =
        Pager(PagingConfig(pageSize = 10, prefetchDistance = 10, enablePlaceholders = false)) {
            RecipePagingSource(remoteSource = recipeRemoteSource, query = query)
        }.flow

    suspend fun getRecipeById(recipeId: String): ApiResponse<Recipe> {
        return recipeRemoteSource.getRecipeById(recipeId = recipeId)
    }

    suspend fun saveRecipe(body: JsonObject): ApiResponse<Recipe> {
        return recipeRemoteSource.saveRecipe(body)
    }

    suspend fun updateRecipeById(recipeId: String, body: JsonObject): ApiResponse<Recipe> {
        return recipeRemoteSource.updateRecipeById(recipeId = recipeId, body = body)
    }

    suspend fun deleteRecipeById(recipeId: String) {
        return recipeRemoteSource.deleteRecipeById(recipeId = recipeId)
    }
}