package com.kira.android.filipinorecipe.features.account.profile

import com.kira.android.filipinorecipe.model.User

data class ProfileUiState(
    val profile: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)