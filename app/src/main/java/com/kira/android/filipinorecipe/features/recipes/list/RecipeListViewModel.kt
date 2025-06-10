package com.kira.android.filipinorecipe.features.recipes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kira.android.filipinorecipe.features.recipes.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
): ViewModel() {

    private val mutableRecipeListState: MutableSharedFlow<RecipeListState> = MutableSharedFlow()
    val recipeListState = mutableRecipeListState.asSharedFlow()

    fun getAllRecipes() {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking { mutableRecipeListState.emit(RecipeListState.ShowError(error)) }
        }) {
            val invoke = recipeUseCase.getAllRecipes()
            mutableRecipeListState.emit(RecipeListState.SetRecipeList(invoke))
        }
    }

    fun deleteRecipeById(recipeId: String) {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking { mutableRecipeListState.emit(RecipeListState.ShowError(error)) }
        }) {
            val invoke = recipeUseCase.deleteRecipeById(recipeId)
            getAllRecipes()
        }
    }
}