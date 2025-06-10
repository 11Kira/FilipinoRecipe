package com.kira.android.filipinorecipe.features.recipes.create

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
class CreateRecipeViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
) : ViewModel() {

    private val mutableCreateRecipeState: MutableSharedFlow<CreateRecipeState> = MutableSharedFlow()
    val createRecipeState = mutableCreateRecipeState.asSharedFlow()

    fun createRecipe() {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking { mutableCreateRecipeState.emit(CreateRecipeState.ShowError(error)) }
        }) {
            //val invoke = recipeUseCase.createRecipe()
            //mutableCreateRecipeState.emit(CreateRecipeState.OnSuccessCreatedRecipe(invoke))
        }
    }
}