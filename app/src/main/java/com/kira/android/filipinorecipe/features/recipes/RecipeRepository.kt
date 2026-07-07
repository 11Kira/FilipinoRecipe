package com.kira.android.filipinorecipe.features.recipes

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.google.gson.JsonObject
import com.kira.android.filipinorecipe.data.local.recipe.RecipeDao
import com.kira.android.filipinorecipe.data.local.recipe.RecipeRemoteMediator
import com.kira.android.filipinorecipe.data.local.recipe.toDomain
import com.kira.android.filipinorecipe.data.local.recipe.toEntity
import com.kira.android.filipinorecipe.model.Recipe
import com.kira.android.filipinorecipe.model.enums.ResponseStatus
import com.kira.android.filipinorecipe.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeRemoteSource: RecipeRemoteSource,
    private val recipeDao: RecipeDao
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getAllRecipes(
        query: String,
        protein: String,
        difficulty: String,
    ): Flow<PagingData<Recipe>> {
        val proteinList = if (protein.isBlank()) emptyList() else protein.split(",")
        val difficultyList = if (difficulty.isBlank()) emptyList() else difficulty.split(",")

        return Pager(
            PagingConfig(
                pageSize = 10,
                prefetchDistance = 2,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            remoteMediator = RecipeRemoteMediator(
                query = query,
                protein = protein,
                difficulty = difficulty,
                remoteSource = recipeRemoteSource,
                recipeDao = recipeDao
            ),
            pagingSourceFactory = {
                recipeDao.getAllRecipesPaging(
                    query = "%$query%",
                    hasProteinFilter = proteinList.isNotEmpty(),
                    proteins = proteinList,
                    hasDifficultyFilter = difficultyList.isNotEmpty(),
                    difficulties = difficultyList
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    suspend fun getRecipeById(recipeId: String): ApiResponse<Recipe> {
        return try {
            val cachedEntity = recipeDao.getRecipeById(recipeId)
            if (cachedEntity != null) {
                return ApiResponse(
                    status = ResponseStatus.SUCCESS,
                    message = "Served from local cache",
                    data = cachedEntity.toDomain()
                )
            }

            val remoteResponse = recipeRemoteSource.getRecipeById(recipeId)

            if (remoteResponse.status == ResponseStatus.SUCCESS && remoteResponse.data != null) {
                recipeDao.insertRecipes(listOf(remoteResponse.data.toEntity()))
            }

            remoteResponse
        } catch (e: Exception) {
            ApiResponse(
                status = ResponseStatus.FAILED,
                message = e.message ?: "An unexpected error occurred"
            )
        }
    }

    suspend fun updateFavoriteStatus(recipeId: String, isFavorited: Boolean) {
        recipeDao.updateFavoriteStatus(recipeId, isFavorited)
    }

    suspend fun saveRecipe(body: JsonObject): ApiResponse<Recipe> {
        return recipeRemoteSource.saveRecipe(body)
    }

    suspend fun updateRecipeById(recipeId: String, body: JsonObject): ApiResponse<Recipe> {
        return recipeRemoteSource.updateRecipeById(recipeId = recipeId, body = body)
    }

    suspend fun deleteRecipeById(recipeId: String) {
        recipeRemoteSource.deleteRecipeById(recipeId = recipeId)
    }
}
