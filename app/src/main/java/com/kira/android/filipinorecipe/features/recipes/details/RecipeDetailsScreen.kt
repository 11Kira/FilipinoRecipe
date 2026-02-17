package com.kira.android.filipinorecipe.features.recipes.details

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kira.android.filipinorecipe.R
import com.kira.android.filipinorecipe.features.component.CircularIconButton
import com.kira.android.filipinorecipe.features.component.DetailsListSection
import com.kira.android.filipinorecipe.features.component.SubDetails
import com.kira.android.filipinorecipe.model.Recipe
import com.kira.android.filipinorecipe.utils.ColorUtils
import kotlinx.coroutines.flow.SharedFlow

lateinit var viewModel: RecipeViewModel

@Composable
fun RecipeDetailsScreen(navController: NavController, id: String) {
    viewModel = hiltViewModel()
    MainScreen(navController, viewModel.recipeState)
    viewModel.getRecipeById(id)
}

@Composable
fun MainScreen(navController: NavController, sharedFlow: SharedFlow<RecipeState>) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }


    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            sharedFlow.collect { state ->
                when (state) {
                    is RecipeState.SetRecipeDetails -> {
                        selectedRecipe = state.recipe
                    }

                    is RecipeState.ShowError -> {

                    }
                }
            }
        }
    }
    selectedRecipe?.let { recipe -> PopulateRecipeDetails(navController, recipe) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopulateRecipeDetails(navController: NavController, recipe: Recipe) {
    val scrollState = rememberScrollState()
    val headerHeight = 500.dp
    val toolbarHeight = 56.dp
    val showToolbarThreshold = with(LocalDensity.current) { (headerHeight - toolbarHeight).toPx() }
    val toolbarAlpha = (scrollState.value / showToolbarThreshold).coerceIn(0f, 1f)
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        RecipeHeaderImage(
            recipe = recipe,
            headerHeight = headerHeight,
            scrollState = scrollState
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Black.copy(alpha = 0.3f), Color.Transparent)
                    )
                )
                .align(Alignment.TopCenter)
        )

        // 2. Scrolling Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Spacer to push content below the image
            Spacer(modifier = Modifier.height(headerHeight))

            // Recipe Details Content
            RecipeContent(recipe)
        }

        // 3. Dynamic Top App Bar
        RecipeTopBar(
            alpha = toolbarAlpha,
            recipeName = recipe.title,
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}

@Composable
fun RecipeHeaderImage(recipe: Recipe, headerHeight: Dp, scrollState: ScrollState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .graphicsLayer {
                // Subtle parallax effect
                translationY = -scrollState.value * 0.5f
                alpha = 1f - (scrollState.value / 1000f).coerceIn(0f, 0.7f)
            }
    ) {
        AsyncImage(
            model = recipe.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // 2. THE SYSTEM SCRIM (Add this code here)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp) // Covers the top area including status bar
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.4f), // Darker at the very top
                            Color.Transparent              // Fades out into the photo
                        )
                    )
                )
                .align(Alignment.TopCenter)
        )
        // Scrim/Gradient to make the bottom text readable
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.3f), // Added mid-stop for smoothness
                            Color.Black.copy(alpha = 0.7f)  // Darkest stop
                        ), // Close the listOf properly here
                        // Using pixels for startY (headerHeight.value is Dp, we need Px)
                        startY = 400f
                    )
                )
        )

        SubDetails(
            recipe = recipe,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun RecipeTopBar(alpha: Float, recipeName: String, onBackClick: () -> Unit) {
    // The parent container covers the status bar + the toolbar area
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = alpha)) // Background extends to the top edge
    ) {
        // 1. A spacer that matches the status bar height
        Spacer(
            modifier = Modifier
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .fillMaxWidth()
        )

        // 2. The actual toolbar content
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CircularIconButton(icon = Icons.Default.ArrowBack, onClick = onBackClick)

            Text(
                text = recipeName,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(alpha = alpha),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp
            )
            CircularIconButton(icon = Icons.Default.FavoriteBorder, onClick = {})
        }
    }
}

@Composable
fun IngredientsSection(recipe: Recipe) {
    Text(
        textAlign = TextAlign.Start,
        fontSize = 25.sp,
        fontFamily = Font(R.font.roboto_medium).toFontFamily(),
        modifier = Modifier
            .wrapContentWidth()
            .padding(bottom = 16.dp),
        text = "Ingredients:",
        color = Color.White
    )
    if (recipe.ingredients.main.isNotEmpty()) {
        DetailsListSection("Main:", recipe.protein, recipe.ingredients.main)
    }
    if (recipe.ingredients.aromatics.isNotEmpty()) {
        DetailsListSection("Aromatics:", recipe.protein, recipe.ingredients.aromatics)
    }
    if (recipe.ingredients.liquidsAndSeasonings.isNotEmpty()) {
        DetailsListSection(
            "Liquids and Seasonings:",
            recipe.protein,
            recipe.ingredients.liquidsAndSeasonings
        )
    }
    if (recipe.ingredients.vegetables.isNotEmpty()) {
        DetailsListSection("Vegetables:", recipe.protein, recipe.ingredients.vegetables)
    }
    if (recipe.ingredients.optionalAddons.isNotEmpty()) {
        DetailsListSection("Optional Add-ons:", recipe.protein, recipe.ingredients.optionalAddons)
    }
}

@Composable
fun RecipeContent(recipe: Recipe) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = ColorUtils().getColorGradientBrush(recipe.protein))
            .padding(16.dp)
    ) {
        Text(
            text = recipe.title,
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = recipe.description,
            color = Color.White.copy(alpha = 0.85f),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            color = ColorUtils().getDividerColor(protein = recipe.protein)
        )
        IngredientsSection(recipe = recipe)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            color = ColorUtils().getDividerColor(protein = recipe.protein)
        )
        DetailsListSection("Steps:", recipe.protein, recipe.steps)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            color = ColorUtils().getDividerColor(protein = recipe.protein)
        )
        DetailsListSection("Cooking Tips:", recipe.protein, recipe.cookingTips)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            color = ColorUtils().getDividerColor(protein = recipe.protein)
        )
        DetailsListSection("Variations:", recipe.protein, recipe.variations)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            color = ColorUtils().getDividerColor(protein = recipe.protein)
        )
        DetailsListSection("Serving Suggestions:", recipe.protein, recipe.servingSuggestions)
    }
}