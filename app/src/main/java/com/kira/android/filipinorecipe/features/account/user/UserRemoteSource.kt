package com.kira.android.filipinorecipe.features.account.user

import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRemoteSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun getAllFavoriteRecipes(
        query: String,
        page: Int
    ) = withContext(Dispatchers.IO) {
        userService.getAllFavoriteRecipes(
            query,
            page
        )
    }

    suspend fun addFavoriteRecipe(
        recipeId: String
    ) = withContext(Dispatchers.IO) {
        userService.addFavoriteRecipe(
            recipeId
        )
    }
}