package com.kira.android.filipinorecipe.features.account.auth.register

sealed class RegisterState {
    data class ShowError(val message: String) : RegisterState()
    object OnRegister : RegisterState()
}