package com.kira.android.filipinorecipe.features.account.auth

import com.kira.android.filipinorecipe.model.Token
import com.kira.android.filipinorecipe.model.request.LoginRequest
import com.kira.android.filipinorecipe.model.request.LogoutRequest
import com.kira.android.filipinorecipe.model.request.RefreshRequest
import com.kira.android.filipinorecipe.model.request.RegisterRequest
import com.kira.android.filipinorecipe.model.response.ApiResponse
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
    fun logout(
        @Body body: LogoutRequest
    ): ApiResponse<Unit>
}