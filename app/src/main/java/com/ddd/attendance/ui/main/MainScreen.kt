package com.ddd.attendance.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ddd.attendance.ui.main.screen.admin.AdminScreen
import com.ddd.attendance.ui.main.screen.member.MemberScreen
import com.ddd.attendance.ui.theme.DDD_WHITE

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DDD_WHITE)
    ) {
        NavHost(
            navController = navController,
            startDestination = ScreenName.MEMBER.name
        ) {
            composable(route = ScreenName.MEMBER.name) {
                MemberScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable(route = ScreenName.ADMIN.name) {
                AdminScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}

enum class ScreenName {
    MEMBER, ADMIN
}