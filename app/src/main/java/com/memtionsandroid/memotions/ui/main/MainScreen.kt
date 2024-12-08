package com.memtionsandroid.memotions.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.main.screen.profile.ProfileScreen
import com.memtionsandroid.memotions.ui.main.screen.starred.StarredScreen
import com.memtionsandroid.memotions.ui.main.screen.statistic.StatisticScreen
import com.memtionsandroid.memotions.ui.main.screen.home.HomeScreen
import com.memtionsandroid.memotions.ui.theme.customColors


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    var selectedRoute by remember { mutableStateOf("home") }
    val isConnected by mainViewModel.isConnected.collectAsState()

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            selectedRoute = backStackEntry.destination.route ?: "home"
        }
    }

    LaunchedEffect(Unit) {
        mainViewModel.getJournals()
        mainViewModel.getCurrentTags()
    }

    val isBarVisible = remember { mutableStateOf(true) }
    val scrollOffset = remember { mutableStateOf(0f) }

    val contentPd by animateDpAsState(
        targetValue = if (isBarVisible.value) if (!isConnected) 90.dp else 80.dp else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )

    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.y
            scrollOffset.value += delta

            if (scrollOffset.value > 10f) {
                isBarVisible.value = true
            } else if (scrollOffset.value < -10f) {
                isBarVisible.value = false
            }

            scrollOffset.value = scrollOffset.value.coerceIn(-100f, 100f)
            return Offset.Zero
        }
    }
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isBarVisible.value,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeOut()
            ) {
                BottomNavigationBar(selectedRoute, isConnected) { itemRoute ->
                    selectedRoute = itemRoute
                    navController.navigate(itemRoute) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        },
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) { innerPadding ->
        innerPadding.calculateTopPadding()
        NavHost(
            navController = navController,
            startDestination = "home",
            Modifier.padding(bottom = contentPd)
        ) {
            composable("home") { HomeScreen(navHostController,mainViewModel) }
            composable("starred") { StarredScreen(navHostController,mainViewModel) }
            composable("statistic") { StatisticScreen() }
            composable("profile") { ProfileScreen(navHostController) }
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedRoute: String,
    isConnected: Boolean,
    onSelectRoute: (itemRoute: String) -> Unit
) {
    val customColors = MaterialTheme.customColors

    val menus = listOf(
        BottomNavItem.Home,
        BottomNavItem.Starred,
        BottomNavItem.Statistic,
        BottomNavItem.Profile
    )
    Column {
        AnimatedVisibility(
            visible = !isConnected,
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(durationMillis = 400, delayMillis = 800)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (!isConnected) customColors.barColor.copy(alpha = 0.8f) else Color.Green),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isConnected) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(id = R.drawable.ic_offline),
                        tint = customColors.onBarColor,
                        contentDescription = "Offline"
                    )
                }
                Text(
                    text = if (!isConnected) "Tidak ada koneksi internet" else "Kembali Online",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 4.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (!isConnected) customColors.onBarColor else customColors.barColor
                )
            }
        }
        NavigationBar(
            modifier = Modifier
                .background(customColors.barColor)
                .fillMaxWidth(),
            containerColor = customColors.barColor,
        ) {
            menus.forEach { item ->
                val isSelected = selectedRoute == item.route

                NavigationBarItem(
                    icon = {
                        when (item.route) {
                            "home" -> Icon(
                                painter = painterResource(id = R.drawable.ic_home),
                                tint = if (isSelected) customColors.onBarColor else customColors.onBarSecondColor,
                                contentDescription = item.label
                            )

                            "starred" -> Icon(
                                painter = painterResource(id = R.drawable.ic_star),
                                tint = if (isSelected) customColors.onBarColor else customColors.onBarSecondColor,
                                contentDescription = item.label
                            )

                            "statistic" -> Icon(
                                painter = painterResource(id = R.drawable.ic_statistic),
                                tint = if (isSelected) customColors.onBarColor else customColors.onBarSecondColor,
                                contentDescription = item.label
                            )

                            "profile" -> Icon(
                                painter = painterResource(id = R.drawable.ic_profile),
                                tint = if (isSelected) customColors.onBarColor else customColors.onBarSecondColor,
                                contentDescription = item.label
                            )
                        }
                    },
                    selected = false,
                    onClick = {
                        onSelectRoute(item.route)
                    }
                )
            }
        }
    }
}

sealed class BottomNavItem(val route: String, val label: String) {
    object Home : BottomNavItem("home", "Home")
    object Starred : BottomNavItem("starred", "Starred")
    object Statistic : BottomNavItem("statistic", "statistic")
    object Profile : BottomNavItem("profile", "profile")
}
