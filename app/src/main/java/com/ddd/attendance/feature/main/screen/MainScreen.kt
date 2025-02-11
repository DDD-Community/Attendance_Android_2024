package com.ddd.attendance.feature.main.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ddd.attendance.feature.admin.screen.AdminScreen
import com.ddd.attendance.feature.main.MainViewModel
import com.ddd.attendance.feature.member.screen.MemberScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = hiltViewModel()

    val startDestination by viewModel.startDestination.collectAsState()

    Column {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(route = ScreenName.MEMBER.name) {
                MemberScreen(
                    navController = navController
                )
            }
            composable(route = ScreenName.ADMIN.name) {
                AdminScreen(
                    navController = navController
                )
            }
        }
    }
}

enum class ScreenName {
    MEMBER, ADMIN, NONE
}