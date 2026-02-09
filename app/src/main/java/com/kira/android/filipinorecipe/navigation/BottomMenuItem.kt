package com.kira.android.filipinorecipe.navigation

import com.kira.android.filipinorecipe.R

sealed class BottomMenuItem(val label: String, val icon: Int, val screenRoute: String) {
    data object Recipes : BottomMenuItem("Recipes", R.drawable.ic_chef_hat, "recipes")
    data object Favorites : BottomMenuItem("Favorites", R.drawable.ic_fav_dish, "favorites")
}