package com.ddd.attendance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ddd.attendance.ui.invitationcode.InvitationCodeScreen
import com.ddd.attendance.ui.login.LoginScreen
import com.ddd.attendance.ui.name.NameScreen
import com.ddd.attendance.ui.theme.DDD_BLACK

@Composable
fun HomeScreen() {
    val navController = rememberNavController()

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
                LoginScreen {
                    navController.navigate(route = ScreenName.INVITATION_CODE.name)
                }
            }
            composable(route = ScreenName.INVITATION_CODE.name) {
                InvitationCodeScreen(
                    onClickBackButton = {
                        navController.popBackStack()
                    },
                    onClickSignup = {
                        navController.navigate(route = ScreenName.NAME.name)
                    }
                )
            }
            composable(route = ScreenName.NAME.name) {
                NameScreen(
                    onClickBackButton = {
                        navController.popBackStack()
                    },
                    onClickNext = {

                    }
                )
            }
        }
    }
}

enum class ScreenName {
    LOGIN,
    INVITATION_CODE,
    NAME
}