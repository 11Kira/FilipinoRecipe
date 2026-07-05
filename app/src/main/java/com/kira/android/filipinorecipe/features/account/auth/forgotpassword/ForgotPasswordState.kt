package com.kira.android.filipinorecipe.features.account.auth.forgotpassword

sealed class ForgotPasswordState {
    data class ShowError(val error: Exception) : ForgotPasswordState()
}