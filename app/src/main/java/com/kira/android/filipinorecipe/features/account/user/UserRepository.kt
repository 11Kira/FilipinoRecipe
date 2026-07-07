package com.kira.android.filipinorecipe.features.account.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kira.android.filipinorecipe.data.local.user.UserDao
import com.kira.android.filipinorecipe.data.local.user.toDomain
import com.kira.android.filipinorecipe.data.local.user.toEntity
import com.kira.android.filipinorecipe.features.recipes.favorites.FavoriteRecipePagingSource
import com.kira.android.filipinorecipe.model.Recipe
import com.kira.android.filipinorecipe.model.User
import com.kira.android.filipinorecipe.model.enums.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteSource: UserRemoteSource,
    private val userDao: UserDao
) {
    val userProfileFlow: Flow<User?> = userDao.getUserProfileFlow().map { it?.toDomain() }

    suspend fun refreshUserProfile() = withContext(Dispatchers.IO) {
        val response = userRemoteSource.getUserProfile()
        if (response.status == ResponseStatus.SUCCESS && response.data != null) {
            userDao.clearUserProfile()
            userDao.insertUserProfile(response.data.toEntity())
        }
        return@withContext response
    }

    suspend fun clearLocalProfile() {
        userDao.clearUserProfile()
    }

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

    suspend fun getUserProfile() = withContext(Dispatchers.IO) { userRemoteSource.getUserProfile() }

}
