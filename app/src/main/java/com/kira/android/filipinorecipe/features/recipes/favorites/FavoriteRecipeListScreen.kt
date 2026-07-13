package com.kira.android.filipinorecipe.features.recipes.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.paging.compose.collectAsLazyPagingItems
import com.kira.android.filipinorecipe.MainViewModel
import com.kira.android.filipinorecipe.component.recipe.RecipeBaseScreen

@Composable
fun FavoriteRecipeListScreen(
    viewModel: FavoriteRecipeListViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
    contentPadding: PaddingValues,
    onItemClick: (String) -> Unit,
) {
    val recipes = viewModel.favoritePagingFlow.collectAsLazyPagingItems()
    val query by viewModel.searchQuery.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val listState = rememberLazyListState()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                recipes.refresh()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    RecipeBaseScreen(
        recipes = recipes,
        query = query,
        listState = listState,
        mainViewModel = mainViewModel,
        screenLabel = "Favorites",
        onQueryChange = { viewModel.onSearchQueryChanged(it) },
        onItemClick = onItemClick,
        contentPadding = contentPadding,
        searchHint = "Search favorites..."
    )
}