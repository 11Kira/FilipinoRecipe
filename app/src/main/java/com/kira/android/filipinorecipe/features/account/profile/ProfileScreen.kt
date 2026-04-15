package com.kira.android.filipinorecipe.features.account.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.kira.android.filipinorecipe.utils.ColorUtils

@Composable
fun Profilecreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    PopulateProfileScreen(
        viewModel = viewModel,
        navController = navController
    )
}

@Composable
fun PopulateProfileScreen(
    viewModel: ProfileViewModel,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = ColorUtils().recipeListBackgroundGradient)
    )
}