package com.kira.android.filipinorecipe.features.account.auth.token

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class TokenManager(context: Context) {
    private val sharedPreferences: SharedPreferences

    companion object {
        private const val PREF_NAME = "secure_prefs"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }

    init {
        sharedPreferences = try {
            createEncryptedSharedPreferences(context)
        } catch (e: Exception) {
            Log.e(
                "TokenManager",
                "Critical error initializing EncryptedSharedPreferences, resetting...",
                e
            )

            // 1. Delete the physical file
            context.deleteSharedPreferences(PREF_NAME)

            // 2. Try one more time
            try {
                createEncryptedSharedPreferences(context)
            } catch (e2: Exception) {
                // 3. Last resort: If it STILL fails, log it and provide a dummy SharedPreferences
                // so the app doesn't crash. The user will simply be "logged out".
                Log.e("TokenManager", "Final attempt failed, returning unencrypted fallback", e2)
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            }
        }
    }

    private fun createEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit {
            putString(KEY_ACCESS_TOKEN, accessToken)
                .putString(KEY_REFRESH_TOKEN, refreshToken)
        }
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
    }

    fun clearTokens() {
        sharedPreferences.edit { clear() }
    }
}