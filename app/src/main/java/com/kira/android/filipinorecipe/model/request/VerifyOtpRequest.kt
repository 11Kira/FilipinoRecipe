package com.kira.android.filipinorecipe.model.request

data class VerifyOtpRequest(
    val email: String,
    val otp: String
)