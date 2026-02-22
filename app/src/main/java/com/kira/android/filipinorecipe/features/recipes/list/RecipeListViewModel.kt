package com.kira.android.filipinorecipe.features.recipes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kira.android.filipinorecipe.features.recipes.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
) : ViewModel() {

    private val _filterTrigger = MutableStateFlow(0)

    private val _selectedProteins = MutableStateFlow<Set<String>>(emptySet())
    val selectedProteins = _selectedProteins.asStateFlow()

    private val _selectedDifficulty = MutableStateFlow<Set<String>>(emptySet())
    val selectedDifficulty = _selectedDifficulty.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val recipePagingFlow = combine(
        _searchQuery.debounce(500L),
        _filterTrigger // This makes the flow wait for a trigger
    ) { query, _ ->
        val proteins = _selectedProteins.value.joinToString(",").lowercase()
        val difficulties = _selectedDifficulty.value.joinToString(",").lowercase()
        Triple(query, proteins, difficulties)
    }.flatMapLatest { (query, proteins, difficulties) ->
        recipeUseCase.getAllRecipes(
            query = query,
            protein = proteins,
            difficulty = difficulties
        )
    }.cachedIn(viewModelScope)

    fun applyFilters() {
        _filterTrigger.value += 1
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun toggleProtein(protein: String) {
        _selectedProteins.update { current ->
            if (current.contains(protein)) current - protein else current + protein
        }
    }

    fun toggleDifficulty(difficulty: String) {
        _selectedDifficulty.update { current ->
            if (current.contains(difficulty)) current - difficulty else current + difficulty
        }
    }

    fun resetFilters() {
        _selectedProteins.value = emptySet()
        _selectedDifficulty.value = emptySet()
    }
}