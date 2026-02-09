package com.kira.android.filipinorecipe.features.recipes.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kira.android.filipinorecipe.features.recipes.RecipeRemoteSource
import com.kira.android.filipinorecipe.features.recipes.model.Recipe

class RecipePagingSource(
    private val remoteSource: RecipeRemoteSource
) : PagingSource<Int, Recipe>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        return try {
            val currentPage = params.key ?: 1
            val response = remoteSource.getAllRecipes(page = currentPage)
            val recipes = response.orEmpty()
            LoadResult.Page(
                data = recipes,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (currentPage < 10) currentPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return null
    }
}