package com.kira.android.filipinorecipe.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
class User(
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
)