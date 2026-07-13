package com.kira.android.filipinorecipe

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import com.kira.android.filipinorecipe.navigation.ForgotPasswordRoute
import com.kira.android.filipinorecipe.navigation.LoginRoute
import com.kira.android.filipinorecipe.navigation.RegisterRoute
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
    val isAuthScreen = currentDestination?.hasRoute<LoginRoute>() == true ||
            currentDestination?.hasRoute<RegisterRoute>() == true ||
            currentDestination?.hasRoute<ForgotPasswordRoute>() == true
    val shouldShowBottomBar = !isDetailScreen && !isAuthScreen
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }
    val isLoggedIn = tokenManager.getAccessToken() != null
    val scope = rememberCoroutineScope()
    var isBottomBarVisibleByScroll by remember { mutableStateOf(true) }

    LaunchedEffect(currentDestination) {
        isBottomBarVisibleByScroll = true
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                if (delta < -15f) {
                    isBottomBarVisibleByScroll = false
                } else if (delta > 15f) {
                    isBottomBarVisibleByScroll = true
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (available.y < 0 && consumed.y == 0f) {
                    isBottomBarVisibleByScroll = true
                }
                return super.onPostScroll(consumed, available, source)
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            AnimatedVisibility(
                visible = shouldShowBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                val density = LocalDensity.current
                val translationY by animateFloatAsState(
                    targetValue = if (isBottomBarVisibleByScroll) 0f else with(density) { 100.dp.toPx() },
                    animationSpec = tween(durationMillis = 250),
                    label = "BottomNavTransition"
                )

                BottomNavigation(
                    modifier = Modifier.graphicsLayer { this.translationY = translationY },
                    navController = navController,
                    isLoggedIn = isLoggedIn,
                    snackbarHostState = snackbarHostState,
                    onReselectActiveTab = {
                        isBottomBarVisibleByScroll = true
                    }
                )
            }
        },
    ) { contentPadding ->
        AppNavHost(
            mainViewModel = viewModel,
            navController = navController,
            contentPadding = contentPadding,
            onShowSnackbar = { message, actionLabel, action ->
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = actionLabel,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) action?.invoke()
                }
            }
        )
    }
}

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController,
    isLoggedIn: Boolean,
    snackbarHostState: SnackbarHostState,
    onReselectActiveTab: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val screens = listOf(
        BottomMenuItem.Recipes,
        BottomMenuItem.Favorites,
        BottomMenuItem.Profile
    )

    NavigationBar(
        containerColor = Color.Transparent,
        modifier = modifier.background(ColorUtils().bottomNavGradient),
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
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Sign in to access this feature",
                                actionLabel = "Sign In",
                                duration = SnackbarDuration.Short
                            )

                            if (result == SnackbarResult.ActionPerformed) {
                                navController.navigate(LoginRoute)
                            }
                        }
                    } else if (!isSelected) {
                        navController.navigate(bottomMenuItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        onReselectActiveTab()
                        viewModel.triggerScrollToTop(bottomMenuItem.label)
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