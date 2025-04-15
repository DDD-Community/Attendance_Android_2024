package com.ddd.attendance.feature.admin.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ddd.attendance.R
import com.ddd.attendance.core.designsystem.DDDMemberSituation
import com.ddd.attendance.core.designsystem.DDDText
import com.ddd.attendance.core.ui.theme.DDD_BLACK
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_20
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_90
import com.ddd.attendance.core.ui.theme.DDD_TEXT_DISABLED
import com.ddd.attendance.core.ui.theme.DDD_WHITE
import com.ddd.attendance.core.utils.noRippleClickable
import com.ddd.attendance.feature.admin.AdminViewModel

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DDD_BLACK)
    ) {
        Header()
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                TeamStatus()
                TeamList(
                    teams = listOf(
                        "Web 1팀",
                        "Web 2팀",
                        "iOS 1팀",
                        "iOS 2팀",
                        "Android 1팀",
                        "Android 2팀",
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            items(items = listOf("김디디", "김디디", "김디디", "김디디", "김디디", "김디디", "김디디", "김디디", "김디디", "김디디", "김디디", "김디디")) {
                MemberCard(
                    member = it,
                    team = "Web1",
                    major = "Designer",
                    attendanceStatus = "출석"
                )
            }
        }
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "출석",
            color = DDD_WHITE,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Row {
            Image(
                modifier = Modifier.noRippleClickable(onClick = /*onPressQrcode*/{}),
                painter = painterResource(R.drawable.ic_36_qr_code),
                contentDescription = "Qr Icon"
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                modifier = Modifier.noRippleClickable(onClick = /*onPressMyInfo*/{}),
                painter = painterResource(R.drawable.ic_36_my_info),
                contentDescription = "Info Icon"
            )
        }
    }
}

@Composable
private fun TeamStatus(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier
        ) {
            DDDText(text = "\uD83D\uDDD3\uFE0F")
            Spacer(modifier = Modifier.width(4.dp))
            DDDText(
                text = "오늘 날짜",
                color = DDD_WHITE,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        DDDMemberSituation(
            attendanceCount = 3,
            tardyCount = 3,
            absentCount = 3,
        )
        Spacer(modifier = Modifier.height(14.dp))
    }
}

@Composable
private fun TeamList(
    modifier: Modifier = Modifier,
    teams: List<String>,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(teams) { index, value ->
            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .then(if (index == 0) Modifier.padding(start = 24.dp) else if (index == teams.lastIndex) Modifier.padding(end = 24.dp) else Modifier),
                text = value,
                color = DDD_WHITE,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun MemberCard(
    modifier: Modifier = Modifier,
    member: String,
    team: String,
    major: String,
    attendanceStatus: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 8.dp)
            .background(
                color = DDD_NEUTRAL_GRAY_90,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
        ) {
            Text(
                text = member,
                color = DDD_WHITE,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Row {
                Text(
                    text = "$team / ",
                    color = DDD_NEUTRAL_GRAY_20,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
                Text(
                    text = major,
                    color = DDD_TEXT_DISABLED,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
            }
        }
        Row(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = attendanceStatus,
                color = DDD_WHITE,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
    }
}

@Preview
@Composable
private fun P1() {
    Content({})
}

@Preview
@Composable
private fun P2() {
    TeamList(
        modifier = Modifier.background(DDD_BLACK),
        teams = listOf(
            "Web 1팀",
            "Web 2팀",
            "iOS 1팀",
            "iOS 2팀",
            "Android 1팀",
            "Android 2팀",
        )
    )
}

@Preview
@Composable
private fun P3() {
    MemberCard(
        member = "김디디",
        team = "Web1",
        major = "Designer",
        attendanceStatus = "출석"
    )
}