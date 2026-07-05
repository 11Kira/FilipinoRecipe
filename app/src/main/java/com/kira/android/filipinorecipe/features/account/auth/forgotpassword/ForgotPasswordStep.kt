package com.kira.android.filipinorecipe.features.account.auth.forgotpassword

sealed interface ForgotPasswordStep {
    data object EnterEmail : ForgotPasswordStep
    data class EnterOtp(val email: String) : ForgotPasswordStep
    data class CreateNewPassword(val email: String, val token: String) : ForgotPasswordStep
    data object Success : ForgotPasswordStep
}