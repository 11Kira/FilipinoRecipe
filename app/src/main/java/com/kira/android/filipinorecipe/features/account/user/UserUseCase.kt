package com.kira.android.filipinorecipe.features.account.user

import androidx.paging.PagingData
import com.kira.android.filipinorecipe.model.Recipe
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun getAllFavoriteRecipes(
        query: String,
    ): Flow<PagingData<Recipe>> = userRepository.getAllFavoriteRecipes(query)
}