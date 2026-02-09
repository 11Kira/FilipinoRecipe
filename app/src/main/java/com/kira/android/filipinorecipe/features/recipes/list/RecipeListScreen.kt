package com.kira.android.filipinorecipe.features.recipes.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kira.android.filipinorecipe.features.recipes.model.Recipe

lateinit var viewModel: RecipeListViewModel

@Composable
fun RecipeListScreen(
    onItemClick: (Long) -> Unit,
) {
    viewModel = hiltViewModel()
    MainRecipeScreen(onItemClick)
}

@Composable
fun MainRecipeScreen(
    onItemClick: (Long) -> Unit,
) {
    val recipes by rememberUpdatedState(newValue = viewModel.recipePagingState.collectAsLazyPagingItems())
    viewModel.getAllRecipes()
    PopulatedRecipeList(recipes, onItemClick)
}

@Composable
fun PopulatedRecipeList(recipeList: LazyPagingItems<Recipe>, onItemClick: (Long) -> Unit) {

}