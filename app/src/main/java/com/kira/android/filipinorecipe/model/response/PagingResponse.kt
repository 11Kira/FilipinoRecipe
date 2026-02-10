package com.kira.android.filipinorecipe.model.response

import com.google.gson.annotations.SerializedName

data class PagingResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("next")
    val next: String? = "",
    @SerializedName("previous")
    val previous: String? = "",
)