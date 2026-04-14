package com.kira.android.filipinorecipe.features.recipes.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.kira.android.filipinorecipe.utils.ColorUtils

lateinit var viewModel: FavoriteRecipeListViewModel

@Composable
fun FavoriteRecipeListScreen(
    navController: NavController
) {
    viewModel = hiltViewModel()
    MainScreen(navController)
}

@Composable
fun MainScreen(navController: NavController) {
    PopulateFavoriteRecipeListScreen(navController)
}

@Composable
fun PopulateFavoriteRecipeListScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = ColorUtils().recipeListBackgroundGradient)
    )
}