package com.kira.android.filipinorecipe.features.account.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kira.android.filipinorecipe.features.recipes.favorites.FavoriteRecipePagingSource
import com.kira.android.filipinorecipe.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteSource: UserRemoteSource
) {
    fun getAllFavoriteRecipes(
        query: String,
    ): Flow<PagingData<Recipe>> =
        Pager(
            PagingConfig(
                pageSize = 10,
                prefetchDistance = 2,
                enablePlaceholders = false,
                initialLoadSize = 10
            )
        ) {
            FavoriteRecipePagingSource(
                remoteSource = userRemoteSource,
                query = query,
            )
        }.flow

    suspend fun toggleFavoriteRecipe(
        recipeId: String
    ) = withContext(Dispatchers.IO) {
        userRemoteSource.toggleFavoriteRecipe(
            recipeId
        )
    }
}