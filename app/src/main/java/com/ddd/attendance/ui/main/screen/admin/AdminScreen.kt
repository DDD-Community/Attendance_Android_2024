package com.ddd.attendance.ui.main.screen.admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ddd.attendance.ui.main.MainViewModel

@Composable
fun AdminScreen(
    navController: NavController,
    viewModel: MainViewModel
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

}