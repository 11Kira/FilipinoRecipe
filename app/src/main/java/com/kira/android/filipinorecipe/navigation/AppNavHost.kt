package com.kira.android.filipinorecipe.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kira.android.filipinorecipe.DetailScreenNavigation
import com.kira.android.filipinorecipe.features.account.auth.login.LoginScreen
import com.kira.android.filipinorecipe.features.account.auth.register.RegisterScreen
import com.kira.android.filipinorecipe.features.recipes.details.RecipeDetailsScreen
import com.kira.android.filipinorecipe.features.recipes.favorites.FavoriteRecipeListScreen
import com.kira.android.filipinorecipe.features.recipes.list.RecipeListScreen
import com.kira.android.filipinorecipe.features.splash.SplashScreen

@Composable
fun AppNavHost(navController: NavHostController, contentPadding: PaddingValues) {
    NavHost(
        navController = navController,
        //startDestination = BottomMenuItem.Recipes.screenRoute
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(BottomMenuItem.Recipes.screenRoute) {
            RecipeListScreen(
                contentPadding,
                onItemClick = { id ->
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        navController.navigate(
                            DetailScreenNavigation(id)
                        )
                    }
                }
            )
        }
        composable(BottomMenuItem.Favorites.screenRoute) {
            FavoriteRecipeListScreen(navController)
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