package com.kira.android.filipinorecipe.features.account.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kira.android.filipinorecipe.features.account.auth.AuthUseCase
import com.kira.android.filipinorecipe.features.account.auth.token.TokenManager
import com.kira.android.filipinorecipe.features.account.user.UserUseCase
import com.kira.android.filipinorecipe.model.enums.ResponseStatus
import com.kira.android.filipinorecipe.model.request.LogoutRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userUseCase: UserUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState = _profileUiState.asStateFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            _profileUiState.update { it.copy(isLoading = true) }
            try {
                val response = userUseCase.getUserProfile()
                if (response.status == ResponseStatus.SUCCESS) {
                    _profileUiState.update {
                        it.copy(
                            profile = response.data,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _profileUiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun logout(refreshToken: String) {
        viewModelScope.launch {
            try {
                authUseCase.logout(LogoutRequest(refreshToken))
            } finally {
                tokenManager.clearTokens()
            }
        }
    }
}