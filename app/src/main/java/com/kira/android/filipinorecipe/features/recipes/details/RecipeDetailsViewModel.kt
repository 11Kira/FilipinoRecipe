package com.kira.android.filipinorecipe.features.recipes.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kira.android.filipinorecipe.features.account.user.UserUseCase
import com.kira.android.filipinorecipe.features.recipes.RecipeUseCase
import com.kira.android.filipinorecipe.model.enums.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _recipeState: MutableSharedFlow<RecipeDetailsState> = MutableSharedFlow()
    val recipeState
        get() = _recipeState.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getRecipeById(recipeId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = recipeUseCase.getRecipeById(recipeId)
                if (response.status == ResponseStatus.SUCCESS) {
                    response.data?.let { data ->
                        _recipeState.emit(RecipeDetailsState.SetRecipeDetails(data))
                    }
                }
            } catch (e: Exception) {
                _recipeState.emit(RecipeDetailsState.ShowError(e))
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavoriteRecipe(recipeId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = userUseCase.toggleFavoriteRecipe(recipeId)
                if (response.status == ResponseStatus.SUCCESS) {
                    response.data?.let { data ->
                        _recipeState.emit(RecipeDetailsState.SetRecipeDetails(data))
                    }
                }
            } catch (e: Exception) {
                _recipeState.emit(RecipeDetailsState.ShowError(e))
            } finally {
                _isLoading.value = false
            }
        }
    }
}