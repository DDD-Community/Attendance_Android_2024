package com.ddd.attendance.feature.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ddd.attendance.core.model.login.GoogleLogin
import com.ddd.attendance.core.ui.theme.DDD_BLACK
import com.ddd.attendance.feature.login.model.RegistrationUiState
import com.ddd.attendance.feature.login.screen.affiliation.AffiliationScreen
import com.ddd.attendance.feature.login.screen.invitationcode.InvitationCodeScreen
import com.ddd.attendance.feature.login.screen.job.JobScreen
import com.ddd.attendance.feature.login.screen.login.LoginScreen
import com.ddd.attendance.feature.login.screen.name.NameScreen

@Composable
fun LoginProcessScreen(
    userInfo: GoogleLogin,
    onClickGoogle:() -> Unit,
) {
    val navController = rememberNavController()
    val viewModel: LoginProcessViewModel = hiltViewModel()

    val registrationUiState by viewModel.registrationUiState.collectAsState()

    // userInfo 갱신 시 ViewModel에 넘김
    LaunchedEffect(userInfo) {
        Log.d("userInfo 값 변경", "회원기입 요청!")
        viewModel.setUserInfo(userInfo)
    }

    // 상태 보고 이동 처리
    LaunchedEffect(registrationUiState) {
        if (registrationUiState is RegistrationUiState.Success) {
            Log.d("registrationUiState", "회원가입 성공!")
        }
    }

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