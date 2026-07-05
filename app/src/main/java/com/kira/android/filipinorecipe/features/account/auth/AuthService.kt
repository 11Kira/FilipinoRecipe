package com.kira.android.filipinorecipe.features.account.auth

import com.kira.android.filipinorecipe.model.Token
import com.kira.android.filipinorecipe.model.request.ForgotPasswordRequest
import com.kira.android.filipinorecipe.model.request.LoginRequest
import com.kira.android.filipinorecipe.model.request.LogoutRequest
import com.kira.android.filipinorecipe.model.request.RefreshRequest
import com.kira.android.filipinorecipe.model.request.RegisterRequest
import com.kira.android.filipinorecipe.model.request.ResetPasswordRequest
import com.kira.android.filipinorecipe.model.request.VerifyOtpRequest
import com.kira.android.filipinorecipe.model.response.ApiResponse
import com.kira.android.filipinorecipe.model.response.OtpVerificationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): ApiResponse<Token>

    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): ApiResponse<Token>

    @POST("auth/refresh")
    fun refreshSync(
        @Body body: RefreshRequest
    ): Call<ApiResponse<Token>>

    @POST("auth/logout")
    suspend fun logout(
        @Body body: LogoutRequest
    ): ApiResponse<Unit>

    @POST("auth/forgot-password")
    suspend fun initiateForgotPassword(
        @Body body: ForgotPasswordRequest
    ): ApiResponse<Unit>

    @POST("auth/verify-otp")
    suspend fun verifyOtpCode(
        @Body body: VerifyOtpRequest
    ): ApiResponse<OtpVerificationResponse>

    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Body body: ResetPasswordRequest
    ): ApiResponse<Unit>
}