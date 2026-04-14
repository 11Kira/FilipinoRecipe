package com.kira.android.filipinorecipe.features.splash

sealed class SplashState {
    data class ShowError(val error: Any) : SplashState()
}