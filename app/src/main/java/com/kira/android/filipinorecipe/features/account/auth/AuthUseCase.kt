package com.kira.android.filipinorecipe.features.account.auth

import com.google.gson.JsonObject
import com.kira.android.filipinorecipe.model.Token
import com.kira.android.filipinorecipe.model.request.LoginRequest
import com.kira.android.filipinorecipe.model.response.ApiResponse
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun register(body: JsonObject): ApiResponse<Token> {
        return authRepository.register(body)
    }

    suspend fun login(body: LoginRequest): ApiResponse<Token> {
        return authRepository.login(body)
    }
}