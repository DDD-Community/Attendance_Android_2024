package com.ddd.attendance.core.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddd.attendance.R
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_90
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_ORANGE_40

@Composable
fun DDDToolTip(
    modifier: Modifier = Modifier,
    text: String,
    cornerRadius: Dp = 8.dp,
    backgroundColor: Color = DDD_NEUTRAL_ORANGE_40,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_polygon_top),
            contentDescription = "Button Icon",
        )

        Card(
            shape = RoundedCornerShape(cornerRadius),
            elevation = cardElevation(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            )
        ) {
            DDDText(
                modifier = Modifier.padding(
                    start = 8.dp,
                    top = 6.dp,
                    end = 8.dp,
                    bottom = 6.dp
                ),
                text = text,
                color = DDD_NEUTRAL_GRAY_90,
                textStyle = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp
            )
        }
    }
}

@Preview(name = "QR 안내 툴팁")
@Composable
private fun P1() {
    DDDToolTip(
        text = "QR스캔으로 출석하세요"
    )
}
