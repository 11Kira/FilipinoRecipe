package com.kira.android.filipinorecipe.navigation

import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute
@Serializable
data object LoginRoute
@Serializable
data object RegisterRoute
@Serializable
data object RecipeListRoute
@Serializable
data object FavoritesRoute

@Serializable
data class DetailScreenNavigation(val id: String)