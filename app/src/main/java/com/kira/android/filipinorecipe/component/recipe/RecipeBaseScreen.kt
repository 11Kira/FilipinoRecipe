package com.kira.android.filipinorecipe.component.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.kira.android.filipinorecipe.model.Recipe
import com.kira.android.filipinorecipe.utils.ColorUtils

@Composable
fun RecipeBaseScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    recipes: LazyPagingItems<Recipe>,
    contentPadding: PaddingValues,
    onItemClick: (String) -> Unit,
    searchHint: String = "Search...",
    filterSlot: @Composable (RowScope.() -> Unit)? = null
) {

    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier
        .fillMaxSize()
        .background(ColorUtils().recipeListBackgroundGradient)) {
        RecipeList(
            recipes = recipes,
            listState = listState,
            searchQuery = query,
            contentPadding = contentPadding,
            onItemClick = onItemClick
        )

        // Floating Header (Search + Optional Filter)
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
                textHint = searchHint,
                onValueChange = onQueryChange,
                onClear = { onQueryChange("") },
                focusManager = focusManager,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            )

            filterSlot?.invoke(this)
        }
    }
}