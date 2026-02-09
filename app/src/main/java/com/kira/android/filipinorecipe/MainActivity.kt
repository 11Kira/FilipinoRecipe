package com.kira.android.filipinorecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kira.android.filipinorecipe.navigation.BottomMenuItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

private lateinit var viewModel: MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val injectedViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel = injectedViewModel
        setContent {
            MainScreenView()
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isDetailScreen = navBackStackEntry?.destination?.route?.startsWith(
        DetailScreenNavigation::class.qualifiedName ?: ""
    ) == true
    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        bottomBar = {
            if (!isDetailScreen) {
                BottomNavigation(navController = navController)
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .background(Color.Black)
                .padding(contentPadding)
                .fillMaxSize()
                .consumeWindowInsets(contentPadding)
                .systemBarsPadding()
        ) {
            //NavigationGraph(navController = navController)
        }

    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    var selectedItem by remember { mutableStateOf("recipe") }
    val selectedTab by remember { mutableStateOf(viewModel.currentlySelectedTab) }

    val screens = listOf(
        BottomMenuItem.Recipes,
        BottomMenuItem.Favorites
    )

    NavigationBar() {
        screens.forEach { bottomMenuItem ->
            NavigationBarItem(
                selected = (selectedItem == selectedTab.collectAsState().value),
                onClick = {
                    selectedItem = bottomMenuItem.label
                    navController.navigate(bottomMenuItem.screenRoute) {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    viewModel.updateSelectedTab(bottomMenuItem.label)
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(bottomMenuItem.icon),
                        contentDescription = bottomMenuItem.label,
                        tint = if (selectedTab.collectAsState().value == bottomMenuItem.label) Color.White else Color.Gray
                    )
                },
                label = {
                    Text(
                        text = bottomMenuItem.label
                    )
                }
            )
        }
    }
}


@Serializable
data class DetailScreenNavigation(
    val id: Long
)