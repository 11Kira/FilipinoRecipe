package com.kira.android.filipinorecipe.features.recipes.list

import com.kira.android.filipinorecipe.features.recipes.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val useCase: RecipeUseCase
){

}