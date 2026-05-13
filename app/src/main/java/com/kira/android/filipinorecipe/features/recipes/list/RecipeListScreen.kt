package com.kira.android.filipinorecipe.features.recipes.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.kira.android.filipinorecipe.component.FilterSheetContent
import com.kira.android.filipinorecipe.component.recipe.RecipeBaseScreen
import com.kira.android.filipinorecipe.component.recipe.RecipeFilter
import com.kira.android.filipinorecipe.utils.ColorUtils
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel = hiltViewModel(),
    contentPadding: PaddingValues,
    onItemClick: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val recipes = viewModel.recipePagingFlow.collectAsLazyPagingItems()
    val query by viewModel.searchQuery.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val selectedProteins by viewModel.selectedProteins.collectAsState()
    val selectedDifficulties by viewModel.selectedDifficulties.collectAsState()
    val appliedFilterCount by viewModel.appliedFilterCount.collectAsState()
    var showFilterSheet by remember { mutableStateOf(false) }

    RecipeBaseScreen(
        recipes = recipes,
        query = query,
        onQueryChange = { viewModel.onSearchQueryChanged(it) },
        onItemClick = onItemClick,
        contentPadding = contentPadding,
        searchHint = "Search recipes...",
        actionSlot = {
            RecipeFilter(
                onButtonClick = {
                    viewModel.syncSelectedWithApplied()
                    showFilterSheet = true
                },
                appliedFilterCount = appliedFilterCount
            )
        }
    )

    if (showFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false },
            sheetState = sheetState,
            containerColor = ColorUtils().pastelMint,
            dragHandle = null,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            FilterSheetContent(
                proteins = listOf("Pork", "Beef", "Chicken", "Seafood", "Vegetables"),
                difficulties = listOf("Easy", "Medium", "Hard"),
                selectedProteins = selectedProteins,
                selectedDifficulties = selectedDifficulties,
                onToggleProtein = { viewModel.toggleProtein(it) },
                onToggleDifficulty = { viewModel.toggleDifficulty(it) },
                onReset = { viewModel.resetFilters() },
                onApply = {
                    viewModel.applyFilters()
                    scope.launch { sheetState.hide() }
                        .invokeOnCompletion { showFilterSheet = false }
                },
                onClose = { showFilterSheet = false }
            )
        }
    }
}