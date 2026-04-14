package com.kira.android.filipinorecipe.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kira.android.filipinorecipe.features.account.auth.token.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {
    private val mutableStartDestination = MutableSharedFlow<StartDestination>()
    val startDestination = mutableStartDestination.asSharedFlow()

    fun checkAuthStatus() {
        viewModelScope.launch {
            delay(1500)
            val token = tokenManager.getAccessToken()
            if (!token.isNullOrBlank()) {
                mutableStartDestination.emit(StartDestination.Home)
            } else {
                mutableStartDestination.emit(StartDestination.Login)
            }
        }
    }
}