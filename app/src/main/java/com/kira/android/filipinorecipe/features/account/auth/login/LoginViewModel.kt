package com.kira.android.filipinorecipe.features.account.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kira.android.filipinorecipe.features.account.auth.AuthUseCase
import com.kira.android.filipinorecipe.model.request.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val mutableLoginState: MutableSharedFlow<LoginState> = MutableSharedFlow()
    val loginState
        get() = mutableLoginState.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking { mutableLoginState.emit(LoginState.ShowError(error)) }
        }) {
            val request = LoginRequest(email, password)
            val invoke = authUseCase.login(request)
            invoke.apply {
                Log.e("ACCESS", data?.accessToken.toString())
                Log.e("REFRESH", data?.refreshToken.toString())
                mutableLoginState.emit(LoginState.OnLogin)
            }
        }
    }
}