package com.ddd.attendance.ui.login.screen.job

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
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
import com.ddd.attendance.ui.login.LoginProcessViewModel
import com.ddd.attendance.ui.login.ScreenName
import com.ddd.attendance.ui.component.DDDNextButton
import com.ddd.attendance.ui.component.DDDProgressbar
import com.ddd.attendance.ui.component.DDDSelector
import com.ddd.attendance.ui.component.DDDText
import com.ddd.attendance.ui.component.DDDTopBar
import com.ddd.attendance.ui.component.TopBarType
import com.ddd.attendance.ui.theme.DDD_BLACK
import com.ddd.attendance.ui.theme.DDD_NEUTRAL_GRAY_20
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
    val list = listOf(
        "Product Manager",
        "Product Designer",
        "Android",
        "iOS",
        "Frontend",
        "Backend",
    )
    var selectedIndex by remember { mutableIntStateOf(value = -1) }

    Scaffold(
        topBar = {
            Column {
                DDDTopBar(
                    type = TopBarType.LEFT_IMAGE_CENTER,
                    onClickLeftImage = onClickBackButton,
                    center = {
                        DDDProgressbar(
                            modifier = Modifier.then(it),
                            current = 2
                        )
                    }
                )
                Spacer(modifier = Modifier.height(54.dp))
            }
        },
        bottomBar = {
            DDDNextButton(
                text = "다음",
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onClickNext,
                isEnabled = selectedIndex != -1
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = DDD_BLACK)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            item {
                DDDText(
                    text = "직무을 선택해주세요",
                    color = DDD_WHITE,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                DDDText(
                    text = "프로젝트 참여하시는 직무을 선택해 주세요.",
                    color = DDD_NEUTRAL_GRAY_20,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
            itemsIndexed(list) { index, value ->
                DDDSelector(
                    text = value,
                    selected = selectedIndex == index,
                    onClick = { selectedIndex = index }
                )
                Spacer(
                    modifier = Modifier.height(
                        if (index != list.lastIndex) 8.dp else 0.dp
                    )
                )
            }
        }
    }
}