package com.kira.android.filipinorecipe.features.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.kira.android.filipinorecipe.navigation.LoginRoute
import com.kira.android.filipinorecipe.navigation.RecipeListRoute
import com.kira.android.filipinorecipe.navigation.SplashRoute
import com.kira.android.filipinorecipe.utils.ColorUtils

lateinit var viewModel: SplashViewModel

@Composable
fun SplashScreen(
    navController: NavController
) {
    viewModel = hiltViewModel()
    MainScreen(navController)
}

@Composable
fun MainScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        viewModel.checkAuthStatus()
    }

    LaunchedEffect(Unit) {
        viewModel.startDestination.collect { state ->
            val route = when (state) {
                is StartDestination.Home -> RecipeListRoute
                is StartDestination.Login -> LoginRoute
            }

            route.let {
                navController.navigate(it) {
                    popUpTo<SplashRoute> { inclusive = true }
                }
            }
        }
    }
    PopulateSplashScreen(navController)
}

@Composable
fun PopulateSplashScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = ColorUtils().recipeListBackgroundGradient)
    )
}