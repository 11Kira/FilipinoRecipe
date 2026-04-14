package com.kira.android.filipinorecipe.features.splash

sealed class StartDestination {
    object Home : StartDestination()
    object Login : StartDestination()
}