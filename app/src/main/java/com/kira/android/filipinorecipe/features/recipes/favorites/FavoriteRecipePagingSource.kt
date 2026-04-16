package com.kira.android.filipinorecipe.features.recipes.favorites

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kira.android.filipinorecipe.features.account.user.UserRemoteSource
import com.kira.android.filipinorecipe.model.Recipe

class FavoriteRecipePagingSource(
    private val remoteSource: UserRemoteSource,
    private val query: String,
) : PagingSource<Int, Recipe>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val position = params.key ?: 1
        return try {
            val response = remoteSource.getAllFavoriteRecipes(
                query = query,
                page = position
            )
            val recipes = response.data ?: emptyList()
            val nextKey = if (recipes.isEmpty() || response.paging.next == null) {
                null
            } else {
                position + 1
            }

            LoadResult.Page(
                data = recipes,
                prevKey = if (position == 1) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return null
    }
}