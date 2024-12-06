package com.memtionsandroid.memotions.ui.main.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.components.profile.AchievementInfo
import com.memtionsandroid.memotions.ui.components.profile.BoxContent
import com.memtionsandroid.memotions.ui.components.profile.JournalInfo
import com.memtionsandroid.memotions.ui.components.profile.ProfileTopBar
import com.memtionsandroid.memotions.ui.components.profile.TitleCardWithIcon
import com.memtionsandroid.memotions.ui.theme.customColors

@Composable
fun ProfileScreen(
    navHostController: NavHostController
) {
    val customColors = MaterialTheme.customColors
    Scaffold(
        topBar = {
            ProfileTopBar()
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                JournalInfo(modifier = Modifier.padding(top = 8.dp))
                AchievementInfo(modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable {
                        navHostController.navigate("achievement")
                    }
                )
                BoxContent(
                    modifier = Modifier.padding(top = 8.dp),
                    header = {
                        TitleCardWithIcon(
                            title = "Pengaturan"
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_setting),
                                contentDescription = "Setting Icon",
                                modifier = Modifier.size(20.dp),
                                tint = customColors.onBackgroundColor
                            )
                        }
                    },
                    content = {}
                )
                BoxContent(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                        },
                    header = {
                        TitleCardWithIcon(
                            title = "Keluar"
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_logout),
                                contentDescription = "Logout Icon",
                                modifier = Modifier.size(20.dp),
                                tint = customColors.onBackgroundColor
                            )
                        }
                    },
                    content = {}
                )
            }
        }
    )
}