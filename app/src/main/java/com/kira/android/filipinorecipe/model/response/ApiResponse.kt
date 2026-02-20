package com.kira.android.filipinorecipe.model.response

import com.google.gson.annotations.SerializedName
import com.kira.android.filipinorecipe.model.enums.ResponseStatus

data class ApiResponse<T>(
    @SerializedName("status")
    val status: ResponseStatus,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("paging")
    val paging: PagingResponse
)