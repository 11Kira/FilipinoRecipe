package com.kira.android.filipinorecipe.features.recipes

import com.google.gson.JsonObject
import com.kira.android.filipinorecipe.model.Recipe
import com.kira.android.filipinorecipe.model.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    @GET("recipes")
    suspend fun getAllRecipes(
        @Query("query") query: String = "",
        @Query("page") page: Int,
        @Query("size") size: Int = 10,
    ): ApiResponse<List<Recipe>>

    @GET("recipes/{id}")
    suspend fun getRecipeById(
        @Path("id") id: String,
    ): ApiResponse<Recipe>

    @POST("recipes")
    suspend fun saveRecipe(
        @Body body: JsonObject
    ): ApiResponse<Recipe>

    @PUT("recipes/{id}")
    suspend fun updateRecipe(
        @Path("id") id: String,
        @Body body: JsonObject
    ): ApiResponse<Recipe>

    @DELETE("recipes/{id}")
    suspend fun deleteRecipeById(
        @Path("id") id: String
    )
}