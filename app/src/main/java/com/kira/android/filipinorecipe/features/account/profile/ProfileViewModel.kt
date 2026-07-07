package com.kira.android.filipinorecipe.features.account.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kira.android.filipinorecipe.features.account.auth.AuthUseCase
import com.kira.android.filipinorecipe.features.account.auth.token.TokenManager
import com.kira.android.filipinorecipe.features.account.user.UserUseCase
import com.kira.android.filipinorecipe.model.enums.ResponseStatus
import com.kira.android.filipinorecipe.model.request.LogoutRequest
import com.kira.android.filipinorecipe.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userUseCase: UserUseCase,
    private val tokenManager: TokenManager,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState = _profileUiState.asStateFlow()

    init {
        // Automatically pipe database cache mutations directly into the UI state
        viewModelScope.launch {
            userUseCase.userProfileFlow.collect { cachedProfile ->
                _profileUiState.update { it.copy(profile = cachedProfile) }
            }
        }
    }

    fun getUserProfile() {
        viewModelScope.launch {
            if (_profileUiState.value.profile == null) {
                _profileUiState.update { it.copy(isLoading = true) }
            }
            try {
                val response = userUseCase.refreshUserProfile()
                _profileUiState.update { it.copy(isLoading = false) }

                if (response.status != ResponseStatus.SUCCESS) {
                    _profileUiState.update { it.copy(error = response.message) }
                }
            } catch (e: Exception) {
                _profileUiState.update { it.copy(isLoading = false) }
                val cachedProfile = userUseCase.userProfileFlow.first()
                if (cachedProfile == null) {
                    val errorMessage = networkUtils.parseNetworkError(e)
                    _profileUiState.update { it.copy(error = errorMessage) }
                } else {
                    println("📡 Network sync failed, but profile cache exists. Suppressing error snackbar.")
                }
            }
        }
    }

    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            try {
                val refreshToken = tokenManager.getRefreshToken() ?: ""
                authUseCase.logout(LogoutRequest(refreshToken.toString()))
            } finally {
                userUseCase.clearLocalProfile()
                tokenManager.clearTokens()
                onLogout.invoke()
            }
        }
    }
}
