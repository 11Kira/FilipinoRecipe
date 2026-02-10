package com.kira.android.filipinorecipe.features.recipes.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dining
import androidx.compose.material.icons.filled.StackedBarChart
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.kira.android.filipinorecipe.features.recipes.enums.Protein
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
    val beef = 0xFFFFB5C0
    val pork = 0xFFFFDBBB
    val chicken = 0xFFFFF9A3
    val seafood = 0xFFB3EBF2
    val vegetables = 0xFFB6F2D1

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(recipeList.itemCount) { index ->
            val recipe = recipeList[index]
            /*val backgroundColor = when (recipe?.protein) {
                Protein.BEEF.toString() -> {
                    Brush.linearGradient(
                        colors = listOf(Color(0xFFFFB5C0), Color(0xFFFFA294)),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                }
                Protein.PORK.toString() -> {
                    Brush.linearGradient(
                        colors = listOf(Color(0xFFFFDBBB), Color(0xFFFFB0A6)),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                }
                Protein.CHICKEN.toString() -> {
                    Brush.linearGradient(
                        colors = listOf(Color(0xFFFFF9A3), Color(0xFFFFD87D)),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                }
                Protein.SEAFOOD.toString() -> {
                    Brush.linearGradient(
                        colors = listOf(Color(0xFFB3EBF2), Color(0xFF85D1DB)),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                }
                Protein.VEGETABLES.toString() -> {
                    Brush.linearGradient(
                        colors = listOf(Color(0xFFB6F2D1), Color(0xFFBED966)),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                }
                else -> {
                    Brush.linearGradient(
                        colors = listOf(Color(0x56ab2f00), Color(0xa8e06300)),
                         start = Offset.Zero,
                        end = Offset.Infinite
                    )
                }
            }*/

            Surface(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)

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
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onItemClick(recipe?.id.toString()) },

                        model = ImageRequest.Builder(LocalContext.current).data(recipe?.image)
                            .crossfade(true).build(),
                        contentDescription = "Recipe",
                        contentScale = ContentScale.Crop,
                        //placeholder = painterResource(id = R.drawable.ic_video),
                        //error = painterResource(id = R.drawable.ic_video)
                    )
                    Text(
                        text = recipe?.title.toString(),
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 5.dp, start = 10.dp, bottom = 2.dp),

                        )
                    Text(
                        text = recipe?.description.toString(),
                        fontSize = 9.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(start = 10.dp, bottom = 5.dp),
                        lineHeight = 10.sp
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                    ) {

                        RoundedTextWithIcon(
                            text = recipe?.protein?.lowercase()?.replaceFirstChar { it.uppercase() }
                                .toString(),
                            Icons.Default.Dining,
                            when (recipe?.protein) {
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
                            text = "${recipe?.estimatedMinutes.toString()} mins",
                            Icons.Default.WatchLater,
                            0xFFB8E986
                        )

                        Spacer(Modifier.size(5.dp))

                        RoundedTextWithIcon(
                            text = recipe?.difficulty?.lowercase()
                                ?.replaceFirstChar { it.uppercase() }.toString(),
                            icon = Icons.Filled.StackedBarChart,
                            0xFFB39DDB
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun RoundedTextWithIcon(text: String, icon: ImageVector, color: Long) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = Color(color),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Time",
            tint = Color.Black,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            text = text,
            color = Color.Black
        )
    }
}