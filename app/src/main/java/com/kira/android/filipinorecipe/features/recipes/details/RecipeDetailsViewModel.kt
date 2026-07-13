package com.kira.android.filipinorecipe.features.recipes.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kira.android.filipinorecipe.features.account.auth.token.TokenManager
import com.kira.android.filipinorecipe.features.account.user.UserUseCase
import com.kira.android.filipinorecipe.features.recipes.RecipeUseCase
import com.kira.android.filipinorecipe.model.Recipe
import com.kira.android.filipinorecipe.model.enums.ResponseStatus
import com.kira.android.filipinorecipe.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val recipeUseCase: RecipeUseCase,
    private val userUseCase: UserUseCase,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    private val _recipeDetailsUiState = MutableStateFlow(RecipeDetailsUiState())
    val recipeDetailsUiState = _recipeDetailsUiState.asStateFlow()
    val isLoggedIn: StateFlow<Boolean> = tokenManager.accessTokenFlow
        .map { token -> token != null }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun getRecipeById(recipeId: String) {
        if (_recipeDetailsUiState.value.recipe?.id == recipeId) return
        viewModelScope.launch {
            _recipeDetailsUiState.update { it.copy(isLoading = true) }
            try {
                val response = recipeUseCase.getRecipeById(recipeId)
                if (response.status == ResponseStatus.SUCCESS) {
                    _recipeDetailsUiState.update {
                        it.copy(
                            recipe = response.data,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                val errorMessage = networkUtils.parseNetworkError(e)
                _recipeDetailsUiState.update { it.copy(error = errorMessage, isLoading = false) }
            }
        }
    }

    fun toggleFavoriteRecipe(recipeId: String) {
        val currentRecipe = _recipeDetailsUiState.value.recipe ?: return
        val wasFavorited = currentRecipe.isFavorited
        val newFavoriteState = !wasFavorited

        _recipeDetailsUiState.update { state ->
            state.copy(recipe = currentRecipe.copy(isFavorited = newFavoriteState))
        }

        viewModelScope.launch {
            try {
                recipeUseCase.updateFavoriteStatus(recipeId, newFavoriteState)
                val response = userUseCase.toggleFavoriteRecipe(recipeId)
                if (response.status != ResponseStatus.SUCCESS) {
                    recipeUseCase.updateFavoriteStatus(recipeId, wasFavorited)
                    rollbackFavorite(
                        currentRecipe,
                        wasFavorited,
                        "Failed to sync favorite with server."
                    )
                }
            } catch (e: Exception) {
                println("📡 Network sync failed, favorite saved locally in Room.")
            }
        }
    }

    private fun rollbackFavorite(
        originalRecipe: Recipe,
        originalState: Boolean,
        errorMessage: String
    ) {
        _recipeDetailsUiState.update {
            it.copy(
                recipe = originalRecipe.copy(isFavorited = originalState),
                error = errorMessage
            )
        }
    }
}
