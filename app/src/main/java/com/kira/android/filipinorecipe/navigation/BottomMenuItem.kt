package com.kira.android.filipinorecipe.navigation

import com.kira.android.filipinorecipe.R

sealed class BottomMenuItem(val label: String, val icon: Int, val route: Any) {
    data object Recipes : BottomMenuItem("Recipes", R.drawable.ic_chef_hat, RecipeListRoute)
    data object Favorites : BottomMenuItem("Favorites", R.drawable.ic_fav_dish, FavoritesRoute)
    data object Account : BottomMenuItem("Account", R.drawable.ic_account, FavoritesRoute)
}