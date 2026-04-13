package com.kira.android.filipinorecipe.features.account.auth

import com.google.gson.JsonObject
import com.kira.android.filipinorecipe.model.request.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteSource: AuthRemoteSource,
) {
    suspend fun register(body: JsonObject) =
        withContext(Dispatchers.IO) { remoteSource.register() }

    suspend fun login(body: LoginRequest) =
        withContext(Dispatchers.IO) { remoteSource.login(body) }
}