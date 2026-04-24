package com.kira.android.filipinorecipe.features.recipes.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.paging.compose.collectAsLazyPagingItems
import com.kira.android.filipinorecipe.component.recipe.RecipeBaseScreen

@Composable
fun FavoriteRecipeListScreen(
    viewModel: FavoriteRecipeListViewModel = hiltViewModel(),
    contentPadding: PaddingValues,
    onItemClick: (String) -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    val recipes = viewModel.favoritePagingFlow.collectAsLazyPagingItems()
    val query by viewModel.searchQuery.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

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

    /*MainFavoriteRecipeScreen(
        viewModel = viewModel,
        recipes = recipes,
        contentPadding = contentPadding,
        onItemClick = onItemClick
    )*/

    RecipeBaseScreen(
        recipes = recipes,
        query = query,
        onQueryChange = { viewModel.onSearchQueryChanged(it) },
        onItemClick = onItemClick,
        contentPadding = contentPadding,
        searchHint = "Search favorites..."
    )
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainFavoriteRecipeScreen(
    viewModel: FavoriteRecipeListViewModel,
    recipes: LazyPagingItems<Recipe>,
    contentPadding: PaddingValues,
    onItemClick: (String) -> Unit
) {
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    var lastScrolledQuery by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(recipes.loadState.refresh) {
        if (recipes.loadState.refresh is LoadState.NotLoading && recipes.itemCount > 0) {
            if (query != lastScrolledQuery) {
                listState.scrollToItem(0)
                lastScrolledQuery = query
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = ColorUtils().recipeListBackgroundGradient)
    ) {
        RecipeList(
            recipes = recipes,
            listState = listState,
            searchQuery = query,
            contentPadding = contentPadding,
            onItemClick = onItemClick,
        )

        // Status bar scrim
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    )
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            RecipeSearchField(
                query = query,
                textHint = "Search favorites...",
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                onClear = { viewModel.onSearchQueryChanged("") },
                focusManager = focusManager,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp)),
            )
        }
    }
}*/
