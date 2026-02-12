package com.kira.android.filipinorecipe.features.recipes.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dining
import androidx.compose.material.icons.filled.StackedBarChart
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kira.android.filipinorecipe.R
import com.kira.android.filipinorecipe.features.recipes.RoundedTextWithIcon
import com.kira.android.filipinorecipe.model.Recipe
import com.kira.android.filipinorecipe.model.enums.Protein
import kotlinx.coroutines.flow.SharedFlow

lateinit var viewModel: RecipeViewModel

@Composable
fun RecipeDetailsScreen(id: String) {
    viewModel = hiltViewModel()
    MainScreen(viewModel.recipeState)
    viewModel.getRecipeById(id)
}

@Composable
fun MainScreen(sharedFlow: SharedFlow<RecipeState>) {
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
    selectedRecipe?.let { recipe -> PopulateRecipeDetails(recipe) }
}

@Composable
fun PopulateRecipeDetails(recipe: Recipe) {
    val beef = 0xFFFFB5C0
    val pork = 0xFFFFDBBB
    val chicken = 0xFFFFF9A3
    val seafood = 0xFFB3EBF2
    val vegetables = 0xFFB6F2D1
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 20.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(recipe.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Recipe image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    RoundedTextWithIcon(
                        text = recipe.protein.lowercase().replaceFirstChar { it.uppercase() },
                        Icons.Default.Dining,
                        when (recipe.protein) {
                            Protein.BEEF.toString() -> {
                                beef
                            }

                            Protein.PORK.toString() -> {
                                pork
                            }

                            Protein.CHICKEN.toString() -> {
                                chicken
                            }

                            Protein.SEAFOOD.toString() -> {
                                seafood
                            }

                            Protein.VEGETABLES.toString() -> {
                                vegetables
                            }

                            else -> {
                                0xFFB8E986
                            }
                        }
                    )

                    Spacer(Modifier.size(5.dp))

                    RoundedTextWithIcon(
                        text = "${recipe.estimatedMinutes} mins",
                        Icons.Default.WatchLater,
                        0xFFB8E986
                    )

                    Spacer(Modifier.size(5.dp))

                    RoundedTextWithIcon(
                        text = recipe.difficulty.lowercase().replaceFirstChar { it.uppercase() },
                        icon = Icons.Filled.StackedBarChart,
                        0xFFB39DDB
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontFamily = Font(R.font.roboto_bold).toFontFamily(),
                    modifier = Modifier.fillMaxWidth(),
                    text = recipe.title,
                    color = Color.White
                )

                Text(
                    textAlign = TextAlign.Start,
                    fontSize = 15.sp,
                    fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 20.dp),
                    text = recipe.description,
                    color = Color.White
                )
                Text(
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    fontFamily = Font(R.font.roboto_medium).toFontFamily(),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 20.dp),
                    text = "Ingredients:",
                    color = Color.White
                )
                if (recipe.ingredients.main.isNotEmpty()) {
                    Text(
                        textAlign = TextAlign.Start,
                        fontSize = 15.sp,
                        fontFamily = Font(R.font.roboto_medium).toFontFamily(),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 4.dp),
                        text = "Main:",
                        color = Color.White
                    )
                    NumberedList(recipe.ingredients.main)
                }
                if (recipe.ingredients.aromatics.isNotEmpty()) {
                    Text(
                        textAlign = TextAlign.Start,
                        fontSize = 15.sp,
                        fontFamily = Font(R.font.roboto_medium).toFontFamily(),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 10.dp),
                        text = "Aromatics:",
                        color = Color.White
                    )
                    NumberedList(recipe.ingredients.aromatics)
                }
                if (recipe.ingredients.liquidsAndSeasonings.isNotEmpty()) {
                    Text(
                        textAlign = TextAlign.Start,
                        fontSize = 15.sp,
                        fontFamily = Font(R.font.roboto_medium).toFontFamily(),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 10.dp),
                        text = "Liquids and Seasonings:",
                        color = Color.White
                    )
                    NumberedList(recipe.ingredients.liquidsAndSeasonings)
                }
                if (recipe.ingredients.vegetables.isNotEmpty()) {
                    Text(
                        textAlign = TextAlign.Start,
                        fontSize = 15.sp,
                        fontFamily = Font(R.font.roboto_medium).toFontFamily(),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 10.dp),
                        text = "Vegetables:",
                        color = Color.White
                    )
                    NumberedList(recipe.ingredients.vegetables)
                }
                if (recipe.ingredients.optionalAddons.isNotEmpty()) {
                    Text(
                        textAlign = TextAlign.Start,
                        fontSize = 15.sp,
                        fontFamily = Font(R.font.roboto_medium).toFontFamily(),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 10.dp),
                        text = "Optional Add-ons:",
                        color = Color.White
                    )
                    NumberedList(recipe.ingredients.optionalAddons)
                }
                Text(
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    fontFamily = Font(R.font.roboto_medium).toFontFamily(),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 20.dp),
                    text = "Steps:",
                    color = Color.White
                )
                NumberedList(recipe.steps)
                Text(
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    fontFamily = Font(R.font.roboto_medium).toFontFamily(),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 20.dp),
                    text = "Cooking Tips:",
                    color = Color.White
                )
                NumberedList(recipe.cookingTips)
                Text(
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    fontFamily = Font(R.font.roboto_medium).toFontFamily(),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 20.dp),
                    text = "Variations:",
                    color = Color.White
                )
                NumberedList(recipe.variations)
                Text(
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    fontFamily = Font(R.font.roboto_medium).toFontFamily(),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(top = 20.dp),
                    text = "Serving Suggestions:",
                    color = Color.White
                )
                NumberedList(recipe.servingSuggestions)
            }


        }
    }
}

@Composable
fun NumberedList(
    items: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "${index + 1}.",
                    fontSize = 15.sp,
                    fontFamily = Font(R.font.roboto_medium).toFontFamily(),
                    color = Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )

                Text(
                    text = item,
                    fontSize = 15.sp,
                    fontFamily = Font(R.font.roboto_regular).toFontFamily(),
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}