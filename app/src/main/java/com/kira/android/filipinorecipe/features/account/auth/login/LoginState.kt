package com.kira.android.filipinorecipe.features.account.auth.login

sealed class LoginState {
    data class ShowError(val error: Exception) : LoginState()
    object OnLogin : LoginState()
}