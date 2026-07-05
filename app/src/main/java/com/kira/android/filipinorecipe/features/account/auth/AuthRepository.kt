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

class AuthRepository @Inject constructor(
    private val remoteSource: AuthRemoteSource,
) {
    suspend fun register(body: RegisterRequest) =
        withContext(Dispatchers.IO) { remoteSource.register(body) }

    suspend fun login(body: LoginRequest) =
        withContext(Dispatchers.IO) { remoteSource.login(body) }

    suspend fun logout(body: LogoutRequest) =
        withContext(Dispatchers.IO) { remoteSource.logout(body) }

    suspend fun initiateForgotPassword(body: ForgotPasswordRequest) =
        withContext(Dispatchers.IO) { remoteSource.initiateForgotPassword(body) }

    suspend fun verifyOtpCode(body: VerifyOtpRequest) =
        withContext(Dispatchers.IO) { remoteSource.verifyOtpCode(body) }

    suspend fun resetPassword(body: ResetPasswordRequest) =
        withContext(Dispatchers.IO) { remoteSource.resetPassword(body) }
}