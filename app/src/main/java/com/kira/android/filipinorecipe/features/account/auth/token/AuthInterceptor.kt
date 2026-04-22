package com.kira.android.filipinorecipe.features.account.auth.token

import com.kira.android.filipinorecipe.features.account.auth.AuthService
import com.kira.android.filipinorecipe.model.request.RefreshRequest
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
    private val authService: Provider<AuthService> // Use Provider!
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (originalRequest.url.encodedPath.contains("auth/refresh")) {
            return chain.proceed(
                originalRequest.newBuilder()
                    .removeHeader("Authorization")
                    .build()
            )
        }

        val accessToken = tokenManager.getAccessToken()
        val authenticatedRequest = originalRequest.newBuilder()
            .apply {
                if (!accessToken.isNullOrBlank()) {
                    header("Authorization", "Bearer $accessToken")
                }
            }.build()

        val response = chain.proceed(authenticatedRequest)
        if (response.code == 403 || response.code == 401) {
            if (response.request.header("Retry-Count") == "1") {
                return response
            }

            synchronized(this) {
                val currentToken = tokenManager.getAccessToken()
                if (currentToken != accessToken) {
                    response.close()
                    return chain.proceed(
                        originalRequest.newBuilder()
                            .header("Authorization", "Bearer $currentToken")
                            .header("Retry-Count", "1") // Prevent infinite retry
                            .build()
                    )
                }

                val refreshToken = tokenManager.getRefreshToken()
                if (!refreshToken.isNullOrBlank()) {
                    val refreshRes = authService.get()
                        .refreshSync(RefreshRequest(refreshToken))
                        .execute()

                    if (refreshRes.isSuccessful) {
                        val body = refreshRes.body()?.data
                        if (body != null) {
                            tokenManager.saveTokens(body.accessToken, body.refreshToken)
                            response.close()

                            return chain.proceed(
                                originalRequest.newBuilder()
                                    .header("Authorization", "Bearer ${body.accessToken}")
                                    .header("Retry-Count", "1") // Tag the retry
                                    .build()
                            )
                        }
                    } else {
                        tokenManager.clearTokens()
                    }
                }
            }
        }

        return response
    }
}