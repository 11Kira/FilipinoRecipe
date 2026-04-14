package com.kira.android.filipinorecipe.features.account.auth

import com.kira.android.filipinorecipe.model.request.LoginRequest
import com.kira.android.filipinorecipe.model.request.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRemoteSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun register(body: RegisterRequest) = withContext(Dispatchers.IO) {
        authService.register(body)
    }

    suspend fun login(body: LoginRequest) = withContext(Dispatchers.IO) {
        authService.login(body)
    }
}