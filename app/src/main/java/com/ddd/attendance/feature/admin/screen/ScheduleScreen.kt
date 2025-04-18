package com.ddd.attendance.feature.admin.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddd.attendance.R
import com.ddd.attendance.core.ui.theme.DDD_BLACK
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_BLUE_20
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_BLUE_40
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_20
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_90
import com.ddd.attendance.core.ui.theme.DDD_WHITE

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    onClickFloatingButton: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Column() {
            MonthScheduleCard()
            MonthScheduleCard()
        }
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 20.dp)
                .size(64.dp)
                .background(color = DDD_NEUTRAL_BLUE_40, shape = CircleShape),
            onClick = onClickFloatingButton
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_40_add),
                contentDescription = "",
                tint = DDD_WHITE
            )
        }
    }

}

@Composable
private fun MonthScheduleCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "7월",
            color = DDD_NEUTRAL_GRAY_20,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )
        ScheduleCard()
        ScheduleCard()
    }
}

@Composable
private fun ScheduleCard(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DDD_NEUTRAL_GRAY_90,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .size(54.dp)
                .background(
                    color = DDD_NEUTRAL_BLUE_20,
                    shape = RoundedCornerShape(12.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "12월",
                color = DDD_BLACK,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "99",
                color = DDD_BLACK,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "정규 세션 2",
                    color = DDD_WHITE,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Text(
                    text = "커리큘럼에 대한 설명 문구 작성",
                    color = DDD_NEUTRAL_GRAY_20,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
            }
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_40_add), contentDescription = "",
                tint = DDD_WHITE
            )
        }
    }
}

@Composable
@Preview
private fun P1() {
    MonthScheduleCard(modifier = Modifier.background(DDD_BLACK))
}

@Composable
@Preview
private fun P2() {
    ScheduleCard()
}

@Composable
@Preview
private fun P3() {
    ScheduleScreen(
        modifier = Modifier.background(DDD_BLACK),
        onClickFloatingButton = {}
    )
}