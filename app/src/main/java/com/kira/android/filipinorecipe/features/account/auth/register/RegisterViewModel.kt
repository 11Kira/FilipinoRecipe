package com.kira.android.filipinorecipe.features.account.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kira.android.filipinorecipe.features.account.auth.AuthUseCase
import com.kira.android.filipinorecipe.model.request.RegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val mutableRegisterState: MutableSharedFlow<RegisterState> = MutableSharedFlow()
    val registerState
        get() = mutableRegisterState.asSharedFlow()

    fun register(email: String, password: String, username: String) {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking { mutableRegisterState.emit(RegisterState.ShowError(error)) }
        }) {
            val request = RegisterRequest(email, password, username)
            val invoke = authUseCase.register(request)
            invoke.apply {
                mutableRegisterState.emit(RegisterState.OnRegister)
            }
        }
    }
}