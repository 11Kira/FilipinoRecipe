package com.kira.android.filipinorecipe.model.response

import com.google.gson.annotations.SerializedName

data class PagingResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("totalPages")
    val totalPages: Int,
    @SerializedName("totalElements")
    val totalElements: Long,
    @SerializedName("hasNext")
    val hasNext: Boolean,
)