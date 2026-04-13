package com.kira.android.filipinorecipe.features.account.auth

import com.kira.android.filipinorecipe.model.Token
import com.kira.android.filipinorecipe.model.request.LoginRequest
import com.kira.android.filipinorecipe.model.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/register")
    suspend fun register(
        // @Body body: JsonObject
    ): ApiResponse<Token>

    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): ApiResponse<Token>
}