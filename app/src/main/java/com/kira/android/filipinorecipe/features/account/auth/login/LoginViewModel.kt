package com.kira.android.filipinorecipe.features.account.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kira.android.filipinorecipe.features.account.auth.AuthUseCase
import com.kira.android.filipinorecipe.features.account.auth.token.TokenManager
import com.kira.android.filipinorecipe.model.request.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _loginState: MutableSharedFlow<LoginState> = MutableSharedFlow()
    val loginState
        get() = _loginState.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = authUseCase.login(LoginRequest(email, password))
                val tokens = response.data
                if (tokens != null) {
                    tokenManager.saveTokens(tokens.accessToken, tokens.refreshToken)
                    _loginState.emit(LoginState.OnLogin)
                } else {
                    _loginState.emit(LoginState.ShowError(Exception("Invalid response from server")))
                }
            } catch (e: Exception) {
                _loginState.emit(LoginState.ShowError(e))
            } finally {
                _isLoading.value = false
            }
        }
    }
}