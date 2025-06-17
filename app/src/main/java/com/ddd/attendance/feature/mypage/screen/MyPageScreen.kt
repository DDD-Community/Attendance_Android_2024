package com.ddd.attendance.feature.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ddd.attendance.R
import com.ddd.attendance.core.designsystem.DDDText
import com.ddd.attendance.core.designsystem.DDDTopBar
import com.ddd.attendance.core.designsystem.TopBarType
import com.ddd.attendance.core.ui.component.DDDProfile
import com.ddd.attendance.core.ui.theme.DDD_BLACK
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_BLUE_40
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_80
import com.ddd.attendance.core.ui.theme.DDD_WHITE
import com.ddd.attendance.feature.mypage.MyPageViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    navController: NavController,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val credit by viewModel.credit.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    Content(
        credit = credit,
        scope = scope,
        creditState = sheetState,
        onCloseCredit = { viewModel.setCredit(false) },
        onOpenCredit = { viewModel.setCredit(true) },
        onBackClicked = {
            navController.popBackStack()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    credit: Boolean,
    scope: CoroutineScope = rememberCoroutineScope(),
    creditState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onBackClicked:() -> Unit,
    onOpenCredit:() -> Unit,
    onCloseCredit:() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DDD_BLACK)
    ) {
        if (credit) {
            CreditModalSheet(
                creditState = creditState,
                onCloseCredit = onCloseCredit
            )
        }

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
                onClickRightImage = onOpenCredit
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditModalSheet(
    creditState: SheetState,
    onCloseCredit: () -> Unit
) {
    ModalBottomSheet(
        sheetState = creditState,
        containerColor = DDD_BLACK,
        shape = RoundedCornerShape(0.dp),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 16.dp)
                    .size(width = 60.dp, height = 4.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(DDD_NEUTRAL_GRAY_80)
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .background(DDD_BLACK),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DDDText(
                    text = "만든 사람들",
                    color = DDD_WHITE,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))


                Column(
                    modifier = Modifier.padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DDDText(
                        text = "Design",
                        color = DDD_WHITE,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    DDDText(
                        text = "강동길, 이지윤, 조재인",
                        color = DDD_WHITE,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    DDDText(
                        text = "iOS",
                        color = DDD_WHITE,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    DDDText(
                        text = "서원지",
                        color = DDD_WHITE,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    DDDText(
                        text = "Android",
                        color = DDD_WHITE,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    DDDText(
                        text = "심은석, 이상훈",
                        color = DDD_WHITE,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    DDDText(
                        text = "Server",
                        color = DDD_WHITE,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    DDDText(
                        text = "조승준",
                        color = DDD_WHITE,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                Box(
                    modifier = Modifier
                        .height(58.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .clip(RoundedCornerShape(99.dp))
                        .background(color = DDD_NEUTRAL_BLUE_40)
                        .clickable {
                            onCloseCredit()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    DDDText(
                        text = "닫기",
                        color = DDD_WHITE,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp
                    )
                }
            }
        },
        onDismissRequest = onCloseCredit
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "멤버 프로필 화면")
@Composable
private fun P1() {
    Content(
        credit = false,
        onOpenCredit = {},
        onCloseCredit = {},
        onBackClicked = {},
    )
}