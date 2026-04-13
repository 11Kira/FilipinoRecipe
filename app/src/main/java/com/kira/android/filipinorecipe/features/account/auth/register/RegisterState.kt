package com.kira.android.filipinorecipe.features.account.auth.register

sealed class RegisterState {
    data class ShowError(val error: Any) : RegisterState()
    object OnRegister : RegisterState()
}