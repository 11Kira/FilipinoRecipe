package com.kira.android.filipinorecipe.features.recipes.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kira.android.filipinorecipe.features.component.SubDetails
import com.kira.android.filipinorecipe.model.Recipe

lateinit var viewModel: RecipeListViewModel

@Composable
fun RecipeListScreen(
    onItemClick: (String) -> Unit,
) {
    viewModel = hiltViewModel()
    MainRecipeScreen(onItemClick)
}

@Composable
fun MainRecipeScreen(
    onItemClick: (String) -> Unit,
) {
    val recipes by rememberUpdatedState(newValue = viewModel.recipePagingState.collectAsLazyPagingItems())
    viewModel.getAllRecipes()
    PopulatedRecipeList(recipes, onItemClick)
}

@Composable
fun PopulatedRecipeList(recipeList: LazyPagingItems<Recipe>, onItemClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 50.dp, start = 10.dp, end = 10.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(recipeList.itemCount) { index ->
            val recipe = recipeList[index]
            recipe?.let { selectedRecipe ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .clickable { onItemClick(recipe.id) },
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(10.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            model = ImageRequest.Builder(LocalContext.current).data(recipe?.image)
                                .crossfade(true).build(),
                            contentDescription = "Recipe",
                            contentScale = ContentScale.Crop,
                        )
                        Text(
                            text = selectedRecipe.title,
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(
                                top = 5.dp,
                                start = 10.dp,
                                end = 10.dp,
                                bottom = 2.dp
                            ),

                            )
                        Text(
                            text = selectedRecipe.description,
                            fontSize = 9.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
                            lineHeight = 10.sp
                        )

                        SubDetails(
                            recipe = selectedRecipe,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 10.dp)
                        )
                    }
                }
            }
        }
    }
}