package com.kira.android.filipinorecipe.features.recipes.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.kira.android.filipinorecipe.model.Recipe
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
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
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

            Spacer(modifier = Modifier.height(15.dp))

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
                fontSize = 15.sp,
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
                fontSize = 15.sp,
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
                fontSize = 15.sp,
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
                fontSize = 15.sp,
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

@Composable
fun NumberedList(
    items: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        items.forEachIndexed { index, item ->
            Text(
                text = "${index + 1}. $item",
                textAlign = TextAlign.Start,
                fontSize = 15.sp,
                fontFamily = Font(R.font.roboto_medium).toFontFamily(),
                modifier = Modifier.padding(vertical = 4.dp),
                color = Color.White
            )
        }
    }
}