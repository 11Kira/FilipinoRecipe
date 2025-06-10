package com.kira.android.filipinorecipe.features.recipes

import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RecipeService {
    @GET("recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @GET("recipes/{id}")
    suspend fun getRecipeById(
        @Path("id") id: String,
    ): Recipe

    @POST("recipes")
    suspend fun saveRecipe(
        @Body body: JsonObject
    ): Recipe

    @PUT("recipes/{id}")
    suspend fun updateRecipe(
        @Path("id") id: String,
        @Body body: JsonObject
    ): Recipe

    @DELETE("recipes/{id}")
    suspend fun deleteRecipe(
        @Path("id") id: String
    )
}