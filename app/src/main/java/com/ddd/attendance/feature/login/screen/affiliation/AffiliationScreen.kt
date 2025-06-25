package com.ddd.attendance.feature.login.screen.affiliation

import android.app.Activity
import android.content.Intent
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ddd.attendance.core.designsystem.DDDNextButton
import com.ddd.attendance.core.designsystem.DDDProgressbar
import com.ddd.attendance.core.designsystem.DDDSelector
import com.ddd.attendance.core.designsystem.DDDText
import com.ddd.attendance.core.designsystem.DDDTopBar
import com.ddd.attendance.core.designsystem.TopBarType
import com.ddd.attendance.core.ui.theme.DDD_BLACK
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_20
import com.ddd.attendance.core.ui.theme.DDD_WHITE
import com.ddd.attendance.feature.login.LoginProcessViewModel
import com.ddd.attendance.feature.login.model.ProfileMeUiState
import com.ddd.attendance.feature.main.MainActivity

@Composable
fun AffiliationScreen(
    navController: NavController,
    viewModel: LoginProcessViewModel
) {
    val context = LocalContext.current as? Activity

    val profileMeUiState by viewModel.profileMeUiState.collectAsState()

    LaunchedEffect(profileMeUiState) {
        if (profileMeUiState is ProfileMeUiState.Success) {
            val result = (profileMeUiState as ProfileMeUiState.Success).data

            if (result.id.isNotBlank()) {
                context?.startActivity(Intent(context, MainActivity::class.java))
                context?.finish()
            }
        }
    }

    Content(
        onClickBackButton = {
            navController.popBackStack()
        },
        onClickNext = { affiliation ->
            viewModel.setUpdateUserAffiliation(affiliation)
            viewModel.registration()
        }
    )
}

@Composable
private fun Content(
    onClickBackButton: () -> Unit,
    onClickNext: (value: String) -> Unit
) {
    val list = listOf(
        "팀 매니징",
        "일정 리마인드",
        "사진 촬영",
        "장소 대관",
        "SNS 관리",
        "출석 체크",
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
                            current = 3
                        )
                    }
                )
                Spacer(modifier = Modifier.height(54.dp))
            }
        },
        bottomBar = {
            DDDNextButton(
                text = "가입 완료",
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onClickNext(
                        list[selectedIndex]
                    )
                },
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
                    text = "운영진/팀원에 따라 타이틀 다름",
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
            itemsIndexed(items = list) { index, value ->
                DDDSelector(
                    text = value,
                    selected = selectedIndex == index,
                    onClick = { selectedIndex = index}
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

@Preview
@Composable
fun AffiliationScreenPreview() {
    Content(
        {}, {}
    )
}