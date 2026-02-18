package com.kira.android.filipinorecipe.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class Ingredients(
    @SerializedName("main")
    val main: List<String>,
    @SerializedName("aromatics")
    val aromatics: List<String>,
    @SerializedName("liquidsAndSeasonings")
    val liquidsAndSeasonings: List<String>,
    @SerializedName("vegetables")
    val vegetables: List<String>,
    @SerializedName("optionalAddons")
    val optionalAddons: List<String>,
)