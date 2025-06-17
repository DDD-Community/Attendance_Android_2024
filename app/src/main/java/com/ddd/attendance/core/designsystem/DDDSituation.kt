package com.ddd.attendance.core.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ddd.attendance.R
import com.ddd.attendance.core.ui.theme.DDD_ERROR
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_20
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_80
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_90
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_ORANGE_40
import com.ddd.attendance.core.ui.theme.DDD_WHITE
import com.ddd.attendance.core.utils.noRippleClickable

@Composable
fun DDDMemberSituation(
    modifier: Modifier = Modifier,
    radius: Dp = 20.dp,
    attendanceCount: Int = 0,
    tardyCount: Int = 0,
    absentCount: Int = 0
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(radius))
            .background(DDD_NEUTRAL_GRAY_90)
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
                Spacer(Modifier.height(48.dp).width(1.dp).background(DDD_NEUTRAL_GRAY_80))
                Spacer(Modifier.weight(1f))

                DDDMemberSituationItem(
                    count = tardyCount,
                    label = stringResource(R.string.member_tardy),
                    textColor = DDD_NEUTRAL_ORANGE_40,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(Modifier.weight(1f))
                Spacer(Modifier.height(48.dp).width(1.dp).background(DDD_NEUTRAL_GRAY_80))
                Spacer(Modifier.weight(1f))

                DDDMemberSituationItem(
                    count = absentCount,
                    label = stringResource(R.string.member_absent),
                    textColor = DDD_ERROR,
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
    onPressMyInfo:() -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 36.dp)
    ) {
        val (logo, qrCode, myInfo, tooltip) = createRefs()

        Image(
            modifier = Modifier.constrainAs(logo) {
                top.linkTo(parent.top, margin = 4.dp)
                start.linkTo(parent.start, margin = 16.dp)
            },
            painter = painterResource(R.drawable.ic_44_logo_black),
            contentDescription = "Arrow Icon"
        )

        Image(
            modifier = Modifier
                .noRippleClickable(onClick = onPressQrcode)
                .constrainAs(qrCode) {
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(myInfo.start, margin = 12.dp)
                },
            painter = painterResource(R.drawable.ic_36_qr_code),
            contentDescription = "Arrow Icon"
        )

        Image(
            modifier = Modifier
                .noRippleClickable(onClick = onPressMyInfo)
                .constrainAs(myInfo) {
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end, margin = 20.dp)
                },
            painter = painterResource(R.drawable.ic_36_my_info),
            contentDescription = "Arrow Icon"
        )

        DDDToolTip(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(tooltip) {
                    start.linkTo(qrCode.start)
                    end.linkTo(qrCode.end)
                    top.linkTo(qrCode.bottom, margin = 8.dp)
                },
            text = "QR스캔으로 출석하세요"
        )
    }
}

@Composable
fun DDDMemberSituationItem(
    modifier: Modifier = Modifier,
    count: Int,
    textColor: Color = DDD_WHITE,
    label: String
) {
    Column(
        modifier = modifier.width(68.dp),
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
            color = DDD_NEUTRAL_GRAY_20,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Preview(name = "공통 카드 타이틀")
@Composable
private fun P1() {
    AttendanceStatusRow(
        onPressQrcode = {},
        onPressMyInfo = {}
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