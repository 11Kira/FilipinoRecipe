package com.kira.android.filipinorecipe.utils

import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class NetworkUtils {
    fun parseNetworkError(e: Exception): String {
        return when (e) {
            is HttpException -> {
                try {
                    // errorBody().string() can only be read once!
                    val errorBody = e.response()?.errorBody()?.string()
                    if (!errorBody.isNullOrBlank()) {
                        val jsonObject = JSONObject(errorBody)
                        // Extract your backend's "message" field
                        jsonObject.getString("message")
                    } else {
                        "An unexpected server error occurred."
                    }
                } catch (parseException: Exception) {
                    "Failed to parse server response."
                }
            }

            is IOException -> "Network error. Please check your internet connection."
            else -> e.localizedMessage ?: "An unknown error occurred."
        }
    }
}
