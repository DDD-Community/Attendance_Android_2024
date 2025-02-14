package com.ddd.attendance.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ddd.attendance.R
import com.ddd.attendance.core.designsystem.DDDToolTip

@Composable
fun ToolTipStatusBar(
    myInfoClicked: () -> Unit,
    qrCodeClicked: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 44.dp, end = 20.dp)
    ) {
        val (myInfo, qrScan, tooltip) = createRefs()

        Image(
            modifier = Modifier.constrainAs(myInfo) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = myInfoClicked
            ),
            painter = painterResource(R.drawable.ic_36_my_info),
            contentDescription = "my info"
        )

        Image(
            modifier = Modifier.constrainAs(qrScan) {
                top.linkTo(myInfo.top)
                end.linkTo(myInfo.start, margin = 8.dp)
                bottom.linkTo(myInfo.bottom)
            }.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = qrCodeClicked
            ),
            painter = painterResource(R.drawable.ic_36_qr_code),
            contentDescription = "qr scan"
        )

        DDDToolTip(
            modifier = Modifier.constrainAs(tooltip) {
                top.linkTo(qrScan.bottom, margin = 10.dp)
                start.linkTo(qrScan.start)
                end.linkTo(qrScan.end)
            },
            text = "QR스캔으로 출석하세요"
        )
    }
}