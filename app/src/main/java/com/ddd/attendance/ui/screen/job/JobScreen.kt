package com.ddd.attendance.ui.screen.job

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ddd.attendance.ui.LoginProcessViewModel
import com.ddd.attendance.ui.ScreenName
import com.ddd.attendance.ui.component.DDDNextButton
import com.ddd.attendance.ui.component.DDDText
import com.ddd.attendance.ui.component.DDDTopBar
import com.ddd.attendance.ui.component.TopBarType
import com.ddd.attendance.ui.theme.DDD_800
import com.ddd.attendance.ui.theme.DDD_BLACK
import com.ddd.attendance.ui.theme.DDD_WHITE

@Composable
fun JobScreen(
    navController: NavController,
    viewModel: LoginProcessViewModel
) {
    Content(
        onClickNext = {
            viewModel.onClickNextFromUserJob()
            navController.navigate(route = ScreenName.AFFILIATION.name)
        },
        onClickBackButton = {
            navController.popBackStack()
        }
    )
}

@Composable
private fun Content(
    onClickBackButton: () -> Unit,
    onClickNext: () -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(value = -1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DDD_BLACK)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = DDD_BLACK)
        ) {
            item {
                DDDTopBar(
                    type = TopBarType.LEFT_IMAGE,
                    onClickLeftImage = onClickBackButton
                )
            }
            item {
                DDDText(
                    text = "직군을 선택해주세요",
                    modifier = Modifier.padding(
                        start = 32.dp,
                        top = 54.dp,
                        bottom = 48.dp
                    ),
                    color = DDD_WHITE,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            items(count = 6) {
                DDDText(
                    text = when (it) {
                        0 -> "PM"
                        1 -> "Designer"
                        2 -> "Android"
                        3 -> "iOS"
                        4 -> "Front-end"
                        5 -> "Back-end"
                        else -> ""
                    },
                    color = if (selectedIndex == it) {
                        DDD_WHITE
                    } else {
                        DDD_800
                    },
                    modifier = Modifier
                        .padding(
                            start = 32.dp,
                            bottom = if (it != 6) { // 나중에 list lastindex로 교체
                                24.dp
                            } else {
                                0.dp
                            }
                        )
                        .clickable {
                            selectedIndex = it
                        },
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
            }
        }
        DDDNextButton(
            text = "다음",
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .fillMaxWidth(),
            onClick = onClickNext,
            isEnabled = selectedIndex != -1
        )
    }
}