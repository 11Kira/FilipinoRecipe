package com.kira.android.filipinorecipe.features.recipes.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kira.android.filipinorecipe.utils.ColorUtils

@Composable
fun FavoriteRecipeListScreen(
    viewModel: FavoriteRecipeListViewModel = hiltViewModel(),
    contentPadding: PaddingValues,
    onItemClick: (String) -> Unit,
) {
    PopulateFavoriteRecipeListScreen(
        viewModel = viewModel,
        onItemClick = onItemClick,
    )
}

@Composable
fun PopulateFavoriteRecipeListScreen(
    viewModel: FavoriteRecipeListViewModel,
    onItemClick: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = ColorUtils().recipeListBackgroundGradient)
    )
}