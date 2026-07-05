package com.kira.android.filipinorecipe.features.account.auth

import com.kira.android.filipinorecipe.model.request.ForgotPasswordRequest
import com.kira.android.filipinorecipe.model.request.LoginRequest
import com.kira.android.filipinorecipe.model.request.LogoutRequest
import com.kira.android.filipinorecipe.model.request.RegisterRequest
import com.kira.android.filipinorecipe.model.request.ResetPasswordRequest
import com.kira.android.filipinorecipe.model.request.VerifyOtpRequest
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

    suspend fun logout(body: LogoutRequest) = withContext(Dispatchers.IO) {
        authService.logout(body)
    }

    suspend fun initiateForgotPassword(body: ForgotPasswordRequest) = withContext(Dispatchers.IO) {
        authService.initiateForgotPassword(body)
    }

    suspend fun verifyOtpCode(body: VerifyOtpRequest) = withContext(Dispatchers.IO) {
        authService.verifyOtpCode(body)
    }

    suspend fun resetPassword(body: ResetPasswordRequest) = withContext(Dispatchers.IO) {
        authService.resetPassword(body)
    }
}