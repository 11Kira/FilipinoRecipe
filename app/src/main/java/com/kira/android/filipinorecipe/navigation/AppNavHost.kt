package com.kira.android.filipinorecipe.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kira.android.filipinorecipe.DetailScreenNavigation
import com.kira.android.filipinorecipe.FavoritesScreen
import com.kira.android.filipinorecipe.features.recipes.details.RecipeDetailsScreen
import com.kira.android.filipinorecipe.features.recipes.list.RecipeListScreen

@Composable
fun AppNavHost(navController: NavHostController, contentPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = BottomMenuItem.Recipes.screenRoute
    ) {
        composable(BottomMenuItem.Recipes.screenRoute) {
            RecipeListScreen(
                contentPadding,
                onItemClick = { id ->
                    navController.navigate(
                        DetailScreenNavigation(id)
                    )
                }
            )
        }
        composable(BottomMenuItem.Favorites.screenRoute) {
            FavoritesScreen()
        }

        composable<DetailScreenNavigation> {
            val args = it.toRoute<DetailScreenNavigation>()
            RecipeDetailsScreen(
                navController,
                args.id,
            )
        }
    }
}