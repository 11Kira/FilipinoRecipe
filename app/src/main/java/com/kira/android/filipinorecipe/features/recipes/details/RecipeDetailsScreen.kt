package com.kira.android.filipinorecipe.features.recipes.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.kira.android.filipinorecipe.model.Recipe
import kotlinx.coroutines.flow.SharedFlow

lateinit var viewModel: RecipeViewModel

@Composable
fun RecipeDetailsScreen(id: String) {
    viewModel = hiltViewModel()
    MainScreen(viewModel.recipeState)
    viewModel.getRecipeById(id)
}

@Composable
fun MainScreen(sharedFlow: SharedFlow<RecipeState>) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }


    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            sharedFlow.collect { state ->
                when (state) {
                    is RecipeState.SetRecipeDetails -> {
                        selectedRecipe = state.recipe
                    }

                    is RecipeState.ShowError -> {

                    }
                }
            }
        }
    }
    selectedRecipe?.let { recipe -> PopulateRecipeDetails(recipe) }
}

@Composable
fun PopulateRecipeDetails(recipe: Recipe) {

}