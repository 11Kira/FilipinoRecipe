package com.kira.android.filipinorecipe.features.recipes.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kira.android.filipinorecipe.features.recipes.RecipeUseCase
import com.kira.android.filipinorecipe.model.enums.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
) : ViewModel() {

    private val mutableRecipeState: MutableSharedFlow<RecipeState> = MutableSharedFlow()
    val recipeState
        get() = mutableRecipeState.asSharedFlow()

    fun getRecipeById(recipeId: String) {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking { mutableRecipeState.emit(RecipeState.ShowError(error)) }
        }) {
            val response = recipeUseCase.getRecipeById(recipeId)
            if (response.status == ResponseStatus.SUCCESS) {
                response.data?.let { data ->
                    mutableRecipeState.emit(RecipeState.SetRecipeDetails(data))
                }
            }
        }
    }

    fun updateRecipeById(recipeId: String) {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking { mutableRecipeState.emit(RecipeState.ShowError(error)) }
        }) {
            //val invoke = recipeUseCase.updateRecipeById(recipeId, )
            //mutableRecipeState.emit(RecipeState.SetRecipeDetails(invoke))
        }
    }

    fun deleteRecipeById(recipeId: String) {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking { mutableRecipeState.emit(RecipeState.ShowError(error)) }
        }) {
            val invoke = recipeUseCase.deleteRecipeById(recipeId)
        }
    }
}