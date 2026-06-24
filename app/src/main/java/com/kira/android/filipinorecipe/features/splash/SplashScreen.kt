package com.kira.android.filipinorecipe.features.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.kira.android.filipinorecipe.navigation.RecipeListRoute
import com.kira.android.filipinorecipe.navigation.SplashRoute
import com.kira.android.filipinorecipe.utils.ColorUtils
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    LaunchedEffect(Unit) {
        delay(1500)
        navController.navigate(RecipeListRoute) {
            popUpTo<SplashRoute> { inclusive = true }
        }
    }
    PopulateSplashScreen()
}

@Composable
fun PopulateSplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = ColorUtils().recipeListBackgroundGradient)
    )
}