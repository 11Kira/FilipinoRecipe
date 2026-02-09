package com.kira.android.filipinorecipe.features.recipes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kira.android.filipinorecipe.features.recipes.RecipeUseCase
import com.kira.android.filipinorecipe.features.recipes.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
) : ViewModel() {

    private val _recipePagingState: MutableStateFlow<PagingData<Recipe>> =
        MutableStateFlow(PagingData.empty())
    val recipePagingState: StateFlow<PagingData<Recipe>>
        get() = _recipePagingState.asStateFlow()

    fun getAllRecipes() {
        viewModelScope.launch(CoroutineExceptionHandler { _, error ->
            runBlocking { }
        }) {
            recipeUseCase.getAllRecipes().cachedIn(viewModelScope)
                .collectLatest { pagingData -> _recipePagingState.value = pagingData }
        }
    }
}