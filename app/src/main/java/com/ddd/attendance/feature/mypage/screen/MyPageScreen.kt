package com.ddd.attendance.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ddd.attendance.R
import com.ddd.attendance.core.designsystem.DDDTopBar
import com.ddd.attendance.core.designsystem.TopBarType
import com.ddd.attendance.core.ui.component.DDDProfile
import com.ddd.attendance.core.ui.theme.DDD_BLACK
import com.ddd.attendance.core.ui.theme.DDD_WHITE
import com.ddd.attendance.feature.mypage.MyPageViewModel

@Composable
fun MyPageScreen(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    Content(
        onBackClicked = {
            navController.popBackStack()
        },
    )
}

@Composable
private fun Content(
    onBackClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DDD_BLACK)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            DDDTopBar(
                type = TopBarType.LEFT_IMAGE_WEIGHT_RIGHT_IMAGE,
                colorFilter = ColorFilter.tint(DDD_WHITE),
                rightImageResource = R.drawable.ic_24_info,
                onClickLeftImage = onBackClicked,
                onClickRightImage = {},
            )

            Spacer(modifier = Modifier.height(11.dp))

            DDDProfile(
                type = "member",
                userName = "김디디",
                jobRole = "Designer",
                team = "Android 2팀",
                cohort = "11기",
            ) {

            }
        }
    }
}

@Preview(name = "멤버 프로필 화면")
@Composable
private fun P1() {
    Content() {}
}