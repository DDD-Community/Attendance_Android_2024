package com.ddd.attendance.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddd.attendance.R
import com.ddd.attendance.ui.theme.DDD_GRAY_F5
import com.ddd.attendance.ui.theme.DDD_NEUTRAL_GRAY_20
import com.ddd.attendance.ui.theme.DDD_NEUTRAL_GRAY_80
import com.ddd.attendance.ui.theme.DDD_TEXT_SECONDARY
import com.ddd.attendance.ui.theme.DDD_WHITE
import com.ddd.attendance.utils.noRippleClickable

@Composable
fun DDDMemberSituation(
    modifier: Modifier = Modifier,
    radius: Dp = 20.dp,
    attendanceCount: Int,
    tardyCount: Int,
    absentCount: Int
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(radius))
            .background(DDD_GRAY_F5)
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 24.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DDDMemberSituationItem(
                    count = attendanceCount,
                    label = stringResource(R.string.member_attendance),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(Modifier.weight(1f))
                Spacer(Modifier.height(48.dp).width(1.dp).background(DDD_NEUTRAL_GRAY_20))
                Spacer(Modifier.weight(1f))

                DDDMemberSituationItem(
                    count = tardyCount,
                    label = stringResource(R.string.member_tardy),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(Modifier.weight(1f))
                Spacer(Modifier.height(48.dp).width(1.dp).background(DDD_NEUTRAL_GRAY_20))
                Spacer(Modifier.weight(1f))

                DDDMemberSituationItem(
                    count = absentCount,
                    label = stringResource(R.string.member_absent),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun AttendanceStatusRow(
    modifier: Modifier = Modifier,
    onPressQrcode:() -> Unit,
    onPressMyPage:() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(DDD_WHITE),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_44_logo_black),
            contentDescription = "Arrow Icon"
        )

        Spacer(modifier = Modifier.weight(1F))

        Image(
            modifier = Modifier.noRippleClickable(onClick = onPressQrcode),
            painter = painterResource(R.drawable.ic_36_qr_code),
            contentDescription = "Arrow Icon"
        )

        Spacer(modifier = Modifier.width(12.dp))

        Image(
            modifier = Modifier.noRippleClickable(onClick = onPressMyPage),
            painter = painterResource(R.drawable.ic_36_my_info),
            contentDescription = "Arrow Icon"
        )
    }
}

@Composable
fun DDDMemberSituationItem(
    modifier: Modifier = Modifier,
    count: Int,
    textColor: Color = DDD_TEXT_SECONDARY,
    label: String
) {
    Column(
        modifier = modifier.width(68.dp).background(DDD_GRAY_F5),
        horizontalAlignment = Alignment.CenterHorizontally) {
        DDDText(
            text = "$count",
            color = textColor,
            textStyle = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(4.dp))

        DDDText(
            text = label,
            textStyle = MaterialTheme.typography.bodyMedium,
            color = DDD_TEXT_SECONDARY,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun DDDAdminSituationItem(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DDD_NEUTRAL_GRAY_80),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 20.dp)
        ) {
            Row (modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically) {
                DDDText(
                    text = text,
                    color = DDD_WHITE,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(Modifier.width(12.dp))

                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_login_logo),
                    contentDescription = "Arrow Icon"
                )
            }

            Spacer(Modifier.height(12.dp))

            Image(
                modifier = Modifier.align(Alignment.End),
                painter = icon,
                contentDescription = "Arrow Icon",
            )
        }

    }
}

@Preview(name = "공통 카드 타이틀")
@Composable
private fun P1() {
    AttendanceStatusRow(
        onPressQrcode = {},
        onPressMyPage = {}
    )
}

@Preview(name = "현황 카드")
@Composable
private fun P2() {
    DDDMemberSituationItem(
        count = 2,
        label = stringResource(R.string.member_absent),
    )
}

@Preview(name = "현황")
@Composable
private fun P3() {
    DDDMemberSituation(
        attendanceCount = 2,
        tardyCount = 3,
        absentCount = 5)
}