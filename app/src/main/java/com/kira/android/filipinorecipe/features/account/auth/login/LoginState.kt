package com.kira.android.filipinorecipe.features.account.auth.login

sealed class LoginState {
    data class ShowError(val message: String) : LoginState()
    object OnLogin : LoginState()
}