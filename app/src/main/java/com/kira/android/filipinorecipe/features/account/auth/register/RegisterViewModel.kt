package com.kira.android.filipinorecipe.features.account.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kira.android.filipinorecipe.features.account.auth.AuthUseCase
import com.kira.android.filipinorecipe.model.request.RegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _registerState: MutableSharedFlow<RegisterState> = MutableSharedFlow()
    val registerState
        get() = _registerState.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    val isInputValid: Boolean
        get() = username.isNotBlank() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                password.length >= 6 &&
                password == confirmPassword

    fun register(email: String, password: String, username: String) {
        if (!isInputValid) return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                authUseCase.register(RegisterRequest(email, password, username))
                _registerState.emit(RegisterState.OnRegister)
            } catch (e: Exception) {
                _registerState.emit(RegisterState.ShowError(e))
            } finally {
                _isLoading.value = false
            }
        }
    }
}