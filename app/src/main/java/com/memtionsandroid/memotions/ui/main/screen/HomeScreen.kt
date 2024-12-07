package com.memtionsandroid.memotions.ui.main.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.NavigationRoutes
import com.memtionsandroid.memotions.ui.components.home.HomeTopBar
import com.memtionsandroid.memotions.ui.components.main.Journals
import com.memtionsandroid.memotions.ui.components.main.journalList
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val customColors = MaterialTheme.customColors
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(NavigationRoutes.ADD_JOURNAL)
                },
                containerColor = customColors.barColor,
                contentColor = customColors.onBarColor,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pen),
                    contentDescription = "Add"
                )
            }
        },
        topBar = {
            HomeTopBar()
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            )
            {
                Journals(journalList)
            }
        }
    )
}




