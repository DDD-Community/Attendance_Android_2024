package com.ddd.attendance.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ddd.attendance.core.model.google.GoogleLogin
import com.ddd.attendance.core.ui.theme.DDD_BLACK
import com.ddd.attendance.feature.login.screen.affiliation.AffiliationScreen
import com.ddd.attendance.feature.login.screen.invitationcode.InvitationCodeScreen
import com.ddd.attendance.feature.login.screen.job.JobScreen
import com.ddd.attendance.feature.login.screen.login.LoginScreen
import com.ddd.attendance.feature.login.screen.name.NameScreen

@Composable
fun LoginProcessScreen(
    snackBarMessage: String?,
    onDismissSnackBar: () -> Unit,
    onClickGoogle: ((GoogleLogin) -> Unit) -> Unit,
) {
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
                    viewModel = viewModel,
                    snackBarMessage = snackBarMessage,
                    onDismissSnackBar = onDismissSnackBar,
                    onClickGoogle = onClickGoogle
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