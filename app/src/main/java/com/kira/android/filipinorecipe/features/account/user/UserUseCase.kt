package com.kira.android.filipinorecipe.features.account.user

import androidx.paging.PagingData
import com.kira.android.filipinorecipe.model.Recipe
import com.kira.android.filipinorecipe.model.User
import com.kira.android.filipinorecipe.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    val userProfileFlow: Flow<User?> = userRepository.userProfileFlow

    suspend fun refreshUserProfile() = userRepository.refreshUserProfile()

    suspend fun clearLocalProfile() {
        userRepository.clearLocalProfile()
    }

    fun getAllFavoriteRecipes(
        query: String,
    ): Flow<PagingData<Recipe>> = userRepository.getAllFavoriteRecipes(query)

    suspend fun toggleFavoriteRecipe(recipeId: String): ApiResponse<Unit> {
        return userRepository.toggleFavoriteRecipe(recipeId)
    }

    suspend fun getUserProfile() = userRepository.getUserProfile()
}
