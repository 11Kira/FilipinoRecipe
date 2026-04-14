package com.kira.android.filipinorecipe.model.request

data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String
)