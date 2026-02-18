package com.kira.android.filipinorecipe.features.recipes.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.kira.android.filipinorecipe.R
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
    val recipes = viewModel.recipePagingFlow.collectAsLazyPagingItems()
    PopulateRecipeList(recipes, onItemClick)
}

@Composable
fun PopulateRecipeList(recipeList: LazyPagingItems<Recipe>, onItemClick: (String) -> Unit) {
    val listState = rememberLazyListState()
    val isRefreshing = recipeList.loadState.refresh is LoadState.Loading

    if (recipeList.itemCount == 0 && isRefreshing) {
        // 1. Show your Shimmer or Loading screen here
        // RecipeShimmerList()
    } else {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = 50.dp,
                start = 10.dp,
                end = 10.dp,
                bottom = 120.dp
            ),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(
                count = recipeList.itemCount,
                key = recipeList.itemKey { it.id }
            ) { index ->
                val recipe = recipeList[index]
                recipe?.let { selectedRecipe ->
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
                                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                                error = painterResource(id = R.drawable.ic_launcher_background)
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
            }
        }
    }
}