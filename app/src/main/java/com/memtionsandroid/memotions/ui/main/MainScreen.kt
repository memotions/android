package com.memtionsandroid.memotions.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.main.screen.HomeScreen
import com.memtionsandroid.memotions.ui.main.screen.ProfileScreen
import com.memtionsandroid.memotions.ui.theme.customColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    // Remember scroll behavior
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, scrollBehavior)
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("starred") { HomeScreen() }
            composable("statistic") { ProfileScreen() }
            composable("Profile") { ProfileScreen() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val customColors = MaterialTheme.customColors

    val menus = listOf(
        BottomNavItem.Home,
        BottomNavItem.Starred,
        BottomNavItem.Statistic,
        BottomNavItem.Profile
    )

    // Remember the selected route to ensure the correct icon is selected
    val selectedRoute = remember { mutableStateOf("home") }

    NavigationBar(
        modifier = Modifier
            .background(customColors.barColor)
            .fillMaxWidth(),
        containerColor = customColors.barColor,
    ) {
        menus.forEach { item ->
            val isSelected = selectedRoute.value == item.route

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
                    // Update the selected route and navigate
                    selectedRoute.value = item.route
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

sealed class BottomNavItem(val route: String, val label: String) {
    object Home : BottomNavItem("home", "Home")
    object Starred : BottomNavItem("starred", "Starred")
    object Statistic : BottomNavItem("statistic", "statistic")
    object Profile : BottomNavItem("profile", "profile")
}
