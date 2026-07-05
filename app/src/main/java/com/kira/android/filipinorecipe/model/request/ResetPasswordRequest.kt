package com.kira.android.filipinorecipe.model.request

data class ResetPasswordRequest(
    val email: String,
    val resetToken: String,
    val newPassword: String
)