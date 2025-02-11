package com.ddd.attendance.feature.admin.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ddd.attendance.core.ui.theme.DDD_BLACK
import com.ddd.attendance.feature.admin.AdminViewModel
import com.ddd.attendance.feature.main.MainViewModel

@Composable
fun AdminScreen(
    navController: NavController,
    viewModel: AdminViewModel = hiltViewModel()
) {
    Content(
        onClickBackButton = {
            navController.popBackStack()
        }
    )
}

@Composable
private fun Content(
    onClickBackButton: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DDD_BLACK)
    ) {

    }
}