package com.kira.android.filipinorecipe.features.account.auth

import com.kira.android.filipinorecipe.model.Token
import com.kira.android.filipinorecipe.model.request.ForgotPasswordRequest
import com.kira.android.filipinorecipe.model.request.LoginRequest
import com.kira.android.filipinorecipe.model.request.LogoutRequest
import com.kira.android.filipinorecipe.model.request.RegisterRequest
import com.kira.android.filipinorecipe.model.request.ResetPasswordRequest
import com.kira.android.filipinorecipe.model.request.VerifyOtpRequest
import com.kira.android.filipinorecipe.model.response.ApiResponse
import com.kira.android.filipinorecipe.model.response.OtpVerificationResponse
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun register(body: RegisterRequest): ApiResponse<Token> {
        return authRepository.register(body)
    }

    suspend fun login(body: LoginRequest): ApiResponse<Token> {
        return authRepository.login(body)
    }

    suspend fun logout(body: LogoutRequest): ApiResponse<Unit> {
        return authRepository.logout(body)
    }

    suspend fun initiateForgotPassword(body: ForgotPasswordRequest): ApiResponse<Unit> {
        return authRepository.initiateForgotPassword(body)
    }

    suspend fun verifyOtpCode(body: VerifyOtpRequest): ApiResponse<OtpVerificationResponse> {
        return authRepository.verifyOtpCode(body)
    }

    suspend fun resetPassword(body: ResetPasswordRequest): ApiResponse<Unit> {
        return authRepository.resetPassword(body)
    }
}