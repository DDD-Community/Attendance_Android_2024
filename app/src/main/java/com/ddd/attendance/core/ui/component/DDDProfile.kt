package com.ddd.attendance.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddd.attendance.core.designsystem.DDDText
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_BLUE_40
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_90
import com.ddd.attendance.core.ui.theme.DDD_TEXT_SECONDARY
import com.ddd.attendance.core.ui.theme.DDD_WHITE

@Composable
fun DDDProfile(
    type: String,
    userName: String,
    jobRole: String,
    team: String,
    cohort: String,
    onLogout:() -> Unit

) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .height(580.dp)
                .fillMaxWidth()
                .padding()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE6F2FF),
                            Color(0xFFD5EAFF),
                            Color(0xFF49ABFF)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                if (type == "admin") {
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .border(
                                width = 1.dp,
                                color = DDD_NEUTRAL_BLUE_40,
                                shape = RoundedCornerShape(99.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        DDDText(
                            modifier = Modifier.padding(start = 14.dp, top = 5.dp, end = 14.dp, bottom = 5.dp),
                            text = "운영진",
                            color = DDD_NEUTRAL_BLUE_40,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }

                DDDText(
                    text = "${userName}님",
                    color = DDD_NEUTRAL_GRAY_90,
                    fontWeight = FontWeight.Bold,
                    fontSize = 44.sp
                )

                Spacer(modifier = Modifier.height(192.dp))

                DDDText(
                    text = "직군",
                    color = DDD_TEXT_SECONDARY,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )

                DDDText(
                    text = jobRole,
                    color = DDD_NEUTRAL_GRAY_90,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                DDDText(
                    text = "소속 팀",
                    color = DDD_TEXT_SECONDARY,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )

                DDDText(
                    text = team,
                    color = DDD_NEUTRAL_GRAY_90,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                DDDText(
                    text = "소속 기수",
                    color = DDD_TEXT_SECONDARY,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )

                DDDText(
                    text = cohort,
                    color = DDD_NEUTRAL_GRAY_90,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                DDDText(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = "Dynamic Developer Designers",
                    color = DDD_TEXT_SECONDARY,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(23.dp))

        Box(
            modifier = Modifier
                .height(48.dp)
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            DDDText(
                modifier = Modifier
                    .drawBehind {
                        val underlineHeight = 1.dp.toPx()
                        val y = size.height

                        drawLine(
                            color = DDD_WHITE,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = underlineHeight
                        )
                    },
                text = "로그아웃",
                color = DDD_WHITE,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(name = "멤버/운영진 프로필 카드")
@Composable
private fun P1() {
    DDDProfile(
        type = "admin",
        userName = "김디디",
        jobRole = "Designer",
        team = "Android 2팀",
        cohort = "11기"
    ) {}
}