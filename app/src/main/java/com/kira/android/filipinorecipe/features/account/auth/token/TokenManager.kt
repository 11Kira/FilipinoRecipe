package com.kira.android.filipinorecipe.features.account.auth.token

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "secure_tokens")

class TokenManager(private val context: Context) {

    private val aead: Aead

    companion object {
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("enc_access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("enc_refresh_token")

        private const val KEYSET_NAME = "master_keyset"
        private const val PREFERENCE_FILE = "tink_keyset_prefs"
        private const val MASTER_KEY_URI = "android-keystore://recipe_master_key"
    }

    init {
        AeadConfig.register()
        val keysetHandle = AndroidKeysetManager.Builder()
            .withSharedPref(context, KEYSET_NAME, PREFERENCE_FILE)
            .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .keysetHandle

        aead = keysetHandle.getPrimitive(Aead::class.java)
    }

    // --- Helper Encryption Methods ---
    private fun encrypt(value: String): String {
        val encryptedBytes = aead.encrypt(value.toByteArray(Charsets.UTF_8), null)
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    private fun decrypt(encryptedValue: String): String {
        val decodedBytes = Base64.decode(encryptedValue, Base64.DEFAULT)
        val decryptedBytes = aead.decrypt(decodedBytes, null)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    // --- Asynchronous API (For UI/ViewModels) ---
    val accessTokenFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_ACCESS_TOKEN]?.let {
            try {
                decrypt(it)
            } catch (e: Exception) {
                null
            }
        }
    }

    val refreshTokenFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_REFRESH_TOKEN]?.let {
            try {
                decrypt(it)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_ACCESS_TOKEN] = encrypt(accessToken)
            prefs[KEY_REFRESH_TOKEN] = encrypt(refreshToken)
        }
    }

    suspend fun clearTokens() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_ACCESS_TOKEN)
            prefs.remove(KEY_REFRESH_TOKEN)
        }
    }

    // --- Synchronous Blocking API (Strictly for OkHttp Interceptor/Authenticator) ---
    fun getAccessTokenSync(): String? = runBlocking {
        accessTokenFlow.firstOrNull()
    }

    fun getRefreshTokenSync(): String? = runBlocking {
        refreshTokenFlow.firstOrNull()
    }

    fun saveTokensSync(accessToken: String, refreshToken: String) = runBlocking {
        saveTokens(accessToken, refreshToken)
    }

    fun clearTokensSync() = runBlocking {
        clearTokens()
    }
}