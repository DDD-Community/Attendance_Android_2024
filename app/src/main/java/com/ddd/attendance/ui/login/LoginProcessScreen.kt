package com.ddd.attendance.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ddd.attendance.ui.login.screen.affiliation.AffiliationScreen
import com.ddd.attendance.ui.login.screen.invitationcode.InvitationCodeScreen
import com.ddd.attendance.ui.login.screen.job.JobScreen
import com.ddd.attendance.ui.login.screen.login.LoginScreen
import com.ddd.attendance.ui.login.screen.name.NameScreen
import com.ddd.attendance.ui.theme.DDD_BLACK

@Composable
fun LoginProcessScreen() {
    val navController = rememberNavController()
    val viewModel: LoginProcessViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DDD_BLACK)
    ) {
        NavHost(
            navController = navController,
            startDestination = ScreenName.LOGIN.name
        ) {
            composable(route = ScreenName.LOGIN.name) {
                LoginScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable(route = ScreenName.INVITATION_CODE.name) {
                InvitationCodeScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable(route = ScreenName.NAME.name) {
                NameScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable(route = ScreenName.JOB.name) {
                JobScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable(route = ScreenName.AFFILIATION.name) {
                AffiliationScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}

enum class ScreenName {
    LOGIN,
    INVITATION_CODE,
    NAME,
    JOB,
    AFFILIATION
}