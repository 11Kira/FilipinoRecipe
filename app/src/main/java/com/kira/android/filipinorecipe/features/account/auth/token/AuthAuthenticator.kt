package com.kira.android.filipinorecipe.features.account.auth.token

import com.kira.android.filipinorecipe.features.account.auth.AuthService
import com.kira.android.filipinorecipe.model.request.RefreshRequest
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val authService: Provider<AuthService>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code != 401) return null
        val refreshToken = tokenManager.getRefreshTokenSync() ?: return null
        val res = authService.get().refreshSync(RefreshRequest(refreshToken)).execute()
        return if (res.isSuccessful) {
            val newTokens = res.body()?.data
            if (newTokens != null) {
                tokenManager.saveTokensSync(newTokens.accessToken, newTokens.refreshToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${newTokens.accessToken}")
                    .build()
            } else {
                null
            }
        } else {
            tokenManager.clearTokensSync()
            null
        }
    }
}