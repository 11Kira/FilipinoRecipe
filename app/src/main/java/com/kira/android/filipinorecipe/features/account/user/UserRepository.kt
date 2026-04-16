package com.kira.android.filipinorecipe.features.account.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kira.android.filipinorecipe.features.recipes.favorites.FavoriteRecipePagingSource
import com.kira.android.filipinorecipe.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteSource: UserRemoteSource
) {
    fun getAllFavoriteRecipes(
        query: String,
    ): Flow<PagingData<Recipe>> =
        Pager(PagingConfig(pageSize = 10, prefetchDistance = 10, enablePlaceholders = false)) {
            FavoriteRecipePagingSource(
                remoteSource = userRemoteSource,
                query = query,
            )
        }.flow
}