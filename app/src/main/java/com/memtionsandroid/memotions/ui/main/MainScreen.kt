package com.memtionsandroid.memotions.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.memtionsandroid.memotions.ui.main.screen.HomeScreen
import com.memtionsandroid.memotions.ui.main.screen.ProfileScreen

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
            composable("search") { HomeScreen() }
            composable("profile") { ProfileScreen() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val menus = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Profile
    )

    NavigationBar {
        menus.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = navController.currentDestination?.route == item.route,
                onClick = {
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

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Search : BottomNavItem("search", Icons.Default.Search, "Search")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}
