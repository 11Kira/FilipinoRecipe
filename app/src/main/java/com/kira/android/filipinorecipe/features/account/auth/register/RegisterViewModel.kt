package com.kira.android.filipinorecipe.features.account.auth.register

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

    fun register(email: String, password: String, username: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = authUseCase.register(RegisterRequest(email, password, username))
                response.apply {
                    _registerState.emit(RegisterState.OnRegister)
                }
            } catch (e: Exception) {
                _registerState.emit(RegisterState.ShowError(e))
            } finally {
                _isLoading.value = false
            }
        }
    }
}