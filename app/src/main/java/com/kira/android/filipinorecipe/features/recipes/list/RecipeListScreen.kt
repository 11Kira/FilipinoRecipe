package com.kira.android.filipinorecipe.features.recipes.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(recipeList.itemCount) { index ->
            val recipe = recipeList[index]
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.Cyan,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .clickable { onItemClick(recipe?.id.toString()) },
                    model = ImageRequest.Builder(LocalContext.current).data(recipe?.image)
                        .crossfade(true).build(),
                    contentDescription = "Recipe",
                    contentScale = ContentScale.Crop,
                    //placeholder = painterResource(id = R.drawable.ic_video),
                    //error = painterResource(id = R.drawable.ic_video)
                )
            }

        }
    }
}