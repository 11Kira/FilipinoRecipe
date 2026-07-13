package com.kira.android.filipinorecipe.features.account.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kira.android.filipinorecipe.features.account.auth.AuthUseCase
import com.kira.android.filipinorecipe.features.account.auth.token.TokenManager
import com.kira.android.filipinorecipe.model.request.LoginRequest
import com.kira.android.filipinorecipe.utils.NetworkUtils
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

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun updateEmail(newValue: String) {
        email = newValue
    }

    fun updatePassword(newValue: String) {
        password = newValue
    }

    val isInputValid: Boolean
        get() = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6

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
                    _loginState.emit(LoginState.ShowError("Invalid response from server"))
                }
            } catch (e: Exception) {
                val errorMessage = NetworkUtils().parseNetworkError(e)
                _loginState.emit(LoginState.ShowError(errorMessage))
            } finally {
                _isLoading.value = false
            }
        }
    }
}