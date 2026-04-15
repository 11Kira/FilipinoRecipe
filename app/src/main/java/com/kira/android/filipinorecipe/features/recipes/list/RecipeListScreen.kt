package com.kira.android.filipinorecipe.features.recipes.list

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import coil3.compose.AsyncImage
import com.kira.android.filipinorecipe.R
import com.kira.android.filipinorecipe.component.SubDetails
import com.kira.android.filipinorecipe.model.Recipe
import com.kira.android.filipinorecipe.utils.ColorUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

lateinit var viewModel: RecipeListViewModel

@Composable
fun RecipeListScreen(
    contentPadding: PaddingValues,
    onItemClick: (String) -> Unit,
) {
    viewModel = hiltViewModel()
    MainRecipeScreen(contentPadding, onItemClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainRecipeScreen(
    contentPadding: PaddingValues,
    onItemClick: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newValue -> newValue != SheetValue.Hidden })
    val recipes = viewModel.recipePagingFlow.collectAsLazyPagingItems()
    val focusManager = LocalFocusManager.current
    val query by viewModel.searchQuery.collectAsState()
    var lastScrolledQuery by rememberSaveable { mutableStateOf("") }
    val selectedProteins by viewModel.selectedProteins.collectAsState()
    val selectedDifficulties by viewModel.selectedDifficulties.collectAsState()
    val appliedFilterCount by viewModel.appliedFilterCount.collectAsState()
    var showFilterSheet by remember { mutableStateOf(false) }

    LaunchedEffect(recipes.loadState.refresh) {
        if (recipes.loadState.refresh is LoadState.NotLoading && recipes.itemCount > 0) {
            if (query != lastScrolledQuery) {
                listState.scrollToItem(0)
                lastScrolledQuery = query
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = ColorUtils().recipeListBackgroundGradient)
    ) {
        PopulateRecipeList(recipes, listState, query, contentPadding, onItemClick)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    )
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            BasicTextField(
                value = query,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp)),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(24.dp))
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.LightGray,
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(modifier = Modifier.weight(1f)) {
                            if (query.isEmpty()) {
                                Text("Search recipes...", color = Color.Gray)
                            }
                            innerTextField()
                        }

                        if (query.isNotEmpty()) {
                            IconButton(
                                onClick = { viewModel.onSearchQueryChanged("") },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            )

            Surface(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(elevation = 4.dp, shape = CircleShape),
                shape = CircleShape,
                color = Color.White
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    IconButton(
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.syncSelectedWithApplied()
                            scope.launch {
                                delay(100)
                                showFilterSheet = true
                                sheetState.show()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter),
                            contentDescription = "Filter",
                            modifier = Modifier.size(24.dp),
                        )
                    }

                    if (appliedFilterCount > 0) {
                        Badge(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 6.dp, end = 6.dp),
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ) {
                            Text(text = appliedFilterCount.toString())
                        }
                    }
                }
            }
        }

        if (showFilterSheet) {
            ModalBottomSheet(
                onDismissRequest = { showFilterSheet = false },
                sheetState = sheetState,
                containerColor = ColorUtils().pastelMint,
                dragHandle = null,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(370.dp)
                        .padding(24.dp)
                        .navigationBarsPadding() // Ensures buttons aren't hidden by system nav
                ) {
                    // Header Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Filter Recipes",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        )
                        IconButton(onClick = { showFilterSheet = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }

                    val proteins = listOf("Pork", "Beef", "Chicken", "Seafood", "Vegetables")
                    val difficulties = listOf("Easy", "Medium", "Hard")

                    Text(
                        "Protein",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    FlowRow(modifier = Modifier.fillMaxWidth()) {
                        proteins.forEach { protein ->
                            FilterChip(
                                label = protein,
                                isSelected = selectedProteins.contains(protein),
                                onToggle = { viewModel.toggleProtein(protein) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        "Difficulty",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    FlowRow(modifier = Modifier.fillMaxWidth()) {
                        difficulties.forEach { level ->
                            FilterChip(
                                label = level,
                                isSelected = selectedDifficulties.contains(level),
                                onToggle = { viewModel.toggleDifficulty(level) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.resetFilters() },
                            modifier = Modifier.weight(1f)
                        ) { Text("Reset") }

                        Button(
                            onClick = {
                                viewModel.applyFilters()
                                scope.launch {
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    showFilterSheet = false
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) { Text("Apply") }
                    }
                }
            }
        }
    }
}

@Composable
fun PopulateRecipeList(
    recipeList: LazyPagingItems<Recipe>,
    listState: LazyListState,
    searchQuery: String,
    contentPadding: PaddingValues,
    onItemClick: (String) -> Unit
) {
    val shimmerBrush = rememberShimmerBrush()
    val isRefreshing = recipeList.loadState.refresh is LoadState.Loading
    val isSearchStale = isRefreshing && recipeList.itemCount > 0
    val isInitialLoad = isRefreshing && recipeList.itemCount == 0
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = 120.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = contentPadding.calculateBottomPadding() + 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            if (isInitialLoad || isSearchStale) {
                items(2) {
                    RecipeShimmerItem(shimmerBrush)
                }
            } else {
                items(
                    count = recipeList.itemCount,
                    key = { index ->
                        val recipe = recipeList[index]
                        "${recipe?.id}_$searchQuery"
                    },
                    contentType = recipeList.itemContentType { "recipe_item" }

                ) { index ->
                    val recipe = recipeList[index]
                    recipe?.let { selectedRecipe ->
                        RecipeCardItem(selectedRecipe, onItemClick)
                    }
                }
            }
        }
    }
}

@Composable
fun rememberShimmerBrush(): Brush {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )
}

@Composable
fun RecipeCardItem(selectedRecipe: Recipe, onItemClick: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(selectedRecipe.id) },
    ) {
        Column {
            AsyncImage(
                model = selectedRecipe.image,
                contentDescription = "Recipe",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = selectedRecipe.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(top = 10.dp, start = 12.dp, end = 12.dp)
            )
            Text(
                text = selectedRecipe.description,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    lineHeight = 16.sp
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )

            SubDetails(
                recipe = selectedRecipe,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun RecipeShimmerItem(shimmerBrush: Brush) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Image Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(shimmerBrush)
            )

            // Title Box
            Box(
                modifier = Modifier
                    .padding(top = 10.dp, start = 12.dp, end = 12.dp)
                    .fillMaxWidth(0.5f)
                    .height(20.dp)
                    .background(shimmerBrush)
            )

            // Description Box
            Box(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(shimmerBrush)
            )

            // SubDetails Tags Row
            Row(
                modifier = Modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                    bottom = 12.dp,
                    top = 4.dp
                )
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(width = 60.dp, height = 24.dp)
                            .clip(CircleShape)
                            .background(shimmerBrush)
                    )
                }
            }
        }
    }
}

@Composable
fun FilterChip(
    label: String,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) Color.Black else Color.White,
        border = BorderStroke(
            1.dp,
            if (isSelected) Color.Black else Color.LightGray.copy(alpha = 0.5f)
        )
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable { onToggle() }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = TextStyle(
                    color = if (isSelected) Color.White else Color.Black,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            )
        }
    }
}