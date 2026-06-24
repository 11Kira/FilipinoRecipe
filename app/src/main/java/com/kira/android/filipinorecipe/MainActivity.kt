package com.kira.android.filipinorecipe

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kira.android.filipinorecipe.features.account.auth.token.TokenManager
import com.kira.android.filipinorecipe.navigation.AppNavHost
import com.kira.android.filipinorecipe.navigation.BottomMenuItem
import com.kira.android.filipinorecipe.navigation.DetailScreenNavigation
import com.kira.android.filipinorecipe.navigation.LoginRoute
import com.kira.android.filipinorecipe.navigation.RegisterRoute
import com.kira.android.filipinorecipe.navigation.SplashRoute
import com.kira.android.filipinorecipe.utils.ColorUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private lateinit var viewModel: MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val injectedViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel = injectedViewModel
        setContent {
            val view = LocalView.current
            if (!view.isInEditMode) {
                SideEffect {
                    val window = (view.context as Activity).window
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                        false
                }
            }
            MainScreenView()
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isDetailScreen = currentDestination?.hasRoute<DetailScreenNavigation>() == true
    val isAuthScreen = currentDestination?.hasRoute<SplashRoute>() == true ||
            currentDestination?.hasRoute<LoginRoute>() == true ||
            currentDestination?.hasRoute<RegisterRoute>() == true
    val shouldShowBottomBar = !isDetailScreen && !isAuthScreen
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }
    val isLoggedIn = tokenManager.getAccessToken() != null
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            AnimatedVisibility(
                visible = shouldShowBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomNavigation(
                    navController = navController,
                    isLoggedIn = isLoggedIn
                )
            }
        },
    ) { contentPadding ->
        AppNavHost(
            navController = navController,
            contentPadding = contentPadding,
            onShowSnackbar = { message ->
                scope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            }
        )
    }
}

@Composable
fun BottomNavigation(
    navController: NavController,
    isLoggedIn: Boolean
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val screens = listOf(
        BottomMenuItem.Recipes,
        BottomMenuItem.Favorites,
        BottomMenuItem.Profile
    )

    NavigationBar(
        containerColor = Color.Transparent,
        modifier = Modifier.background(ColorUtils().bottomNavGradient),
        windowInsets = WindowInsets.navigationBars
    ) {
        screens.forEach { bottomMenuItem ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(bottomMenuItem.route::class)
            } == true
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    val restrictedRoutes = listOf(
                        BottomMenuItem.Favorites.route::class,
                        BottomMenuItem.Profile.route::class
                    )
                    val isRestricted = restrictedRoutes.any { it == bottomMenuItem.route::class }
                    if (isRestricted && !isLoggedIn) {
                        // Redirect to Login if they aren't logged in
                        navController.navigate(LoginRoute)
                    } else if (!isSelected) {
                        navController.navigate(bottomMenuItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        viewModel.updateSelectedTab(bottomMenuItem.label)
                    }
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(bottomMenuItem.icon),
                        contentDescription = bottomMenuItem.label,
                        modifier = Modifier.run { size(if (isSelected) 26.dp else 24.dp) }
                    )
                },
                label = {
                    Text(
                        text = bottomMenuItem.label,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    // The "Pill" background color
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                    // Active State Colors
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    // Inactive State Colors (Muted)
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            )
        }
    }
}