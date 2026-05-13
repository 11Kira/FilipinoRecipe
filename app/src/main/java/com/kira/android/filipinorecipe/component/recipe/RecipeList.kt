package com.kira.android.filipinorecipe.component.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.kira.android.filipinorecipe.model.Recipe

@Composable
fun RecipeList(
    recipes: LazyPagingItems<Recipe>,
    listState: LazyListState,
    searchQuery: String,
    contentPadding: PaddingValues,
    onItemClick: (String) -> Unit
) {
    val shimmerBrush = RecipeShimmerBrush()
    val loadState = recipes.loadState.refresh
    val isInitialLoad = loadState is LoadState.Loading && recipes.itemCount == 0

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            top = 120.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = contentPadding.calculateBottomPadding() + 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        if (isInitialLoad) {
            items(3) { RecipeShimmerItem(shimmerBrush) }
        } else {
            items(
                count = recipes.itemCount,
                key = recipes.itemKey { it.id },
                contentType = recipes.itemContentType { "recipe_item" }
            ) { index ->
                recipes[index]?.let {
                    RecipeCardItem(
                        selectedRecipe = it,
                        onItemClick = onItemClick
                    )
                }
            }

            if (recipes.loadState.append is LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}