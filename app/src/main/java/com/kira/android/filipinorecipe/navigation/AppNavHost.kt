package com.kira.android.filipinorecipe.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kira.android.filipinorecipe.features.account.auth.login.LoginScreen
import com.kira.android.filipinorecipe.features.account.auth.register.RegisterScreen
import com.kira.android.filipinorecipe.features.account.profile.Profilecreen
import com.kira.android.filipinorecipe.features.recipes.details.RecipeDetailsScreen
import com.kira.android.filipinorecipe.features.recipes.favorites.FavoriteRecipeListScreen
import com.kira.android.filipinorecipe.features.recipes.list.RecipeListScreen
import com.kira.android.filipinorecipe.features.splash.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    contentPadding: PaddingValues,
    onShowSnackbar: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ) {
        composable<SplashRoute> { SplashScreen(navController) }
        composable<LoginRoute> {
            LoginScreen(
                navController = navController,
                onShowSnackbar = onShowSnackbar
            )
        }
        composable<RegisterRoute> {
            RegisterScreen(
                navController = navController,
                onShowSnackbar = onShowSnackbar
            )
        }
        composable<RecipeListRoute> {
            RecipeListScreen(
                contentPadding,
                onItemClick = { id ->
                    navController.navigate(DetailScreenNavigation(id))
                }
            )
        }

        composable<FavoritesRoute> {
            FavoriteRecipeListScreen(navController)
        }

        composable<AccountRoute> {
            Profilecreen(navController)
        }

        composable<DetailScreenNavigation> { backStackEntry ->
            val args = backStackEntry.toRoute<DetailScreenNavigation>()
            RecipeDetailsScreen(navController, args.id)
        }
    }
}