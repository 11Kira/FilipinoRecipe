package com.kira.android.filipinorecipe.features.account.user

import com.kira.android.filipinorecipe.model.Recipe
import com.kira.android.filipinorecipe.model.response.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("users/favorites")
    suspend fun getAllFavoriteRecipes(
        @Query("query") query: String = "",
        @Query("page") page: Int,
        @Query("size") size: Int = 10,
    ): ApiResponse<List<Recipe>>
}