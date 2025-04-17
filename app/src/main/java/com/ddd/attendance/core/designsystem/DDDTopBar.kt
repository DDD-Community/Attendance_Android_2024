package com.ddd.attendance.core.designsystem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddd.attendance.R
import com.ddd.attendance.core.ui.theme.DDD_400
import com.ddd.attendance.core.ui.theme.DDD_WHITE

@Composable
fun DDDTopBar(
    modifier: Modifier = Modifier,
    type: TopBarType = TopBarType.LEFT_IMAGE,
    @DrawableRes rightImageResource: Int = 0,
    rightText: String = "",
    drawableResourceList: List<Int> = emptyList(),
    onClickLeftImage: () -> Unit = {},
    onClickRightImage: () -> Unit = {},
    onClickRightText: () -> Unit = {},
    colorFilter: ColorFilter? = null,
    center: @Composable (Modifier) -> Unit = {}
) {
    when (type) {
        TopBarType.LEFT_IMAGE -> DDDTopBar(
            isVisibleRightImage = false,
            modifier = modifier,
            onClickLeftImage = onClickLeftImage
        )

        TopBarType.LEFT_RIGHT_IMAGE -> DDDTopBar(
            modifier = modifier,
            isVisibleRightImage = true,
            rightImageResource = rightImageResource,
            onClickLeftImage = onClickLeftImage,
            onClickRightImage = onClickRightImage
        )

        TopBarType.LEFT_IMAGE_CENTER -> DDDTopBar(
            modifier = modifier,
            onClickLeftImage = onClickLeftImage,
            isVisibleRightImage = false,
            center = center,
        )

        TopBarType.LEFT_IMAGE_RIGHT_TEXT -> TextTopBar(
            text = rightText,
            modifier = modifier,
            onClickLeftImage = onClickLeftImage,
            onClickRightText = onClickRightText
        )

        TopBarType.IMAGE -> NonBackButtonTopBar(
            drawableResourceList = drawableResourceList,
            modifier = modifier
        )

        TopBarType.LEFT_IMAGE_WEIGHT_RIGHT_IMAGE -> DDDBasicTopBar(
            modifier = modifier,
            colorFilter = colorFilter,
            onClickLeftImage = onClickLeftImage,
            onClickRightImage = onClickRightImage
        )
    }
}

@Composable
private fun DDDTopBar(
    isVisibleRightImage: Boolean,
    modifier: Modifier = Modifier,
    @DrawableRes rightImageResource: Int = 0,
    onClickLeftImage: () -> Unit = {},
    onClickRightImage: () -> Unit = {},
    center: @Composable (Modifier) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 56.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(40.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_40_back),
                contentDescription = null,
                modifier = Modifier
                    .clickable(onClick = onClickLeftImage)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        center(Modifier.weight(1f))
        /*DDDProgressbar(
            modifier = Modifier.weight(1f),
            current = 1
        )*/

        Spacer(modifier = Modifier.width(12.dp))

        Box(modifier = Modifier.size(40.dp)) {
            if (isVisibleRightImage) {
                Image(
                    painter = painterResource(id = rightImageResource),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(onClick = onClickRightImage)
                )
            }
        }
    }
}

@Composable
private fun NonBackButtonTopBar(
    drawableResourceList: List<Int>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 56.dp)
    ) {
        Spacer(modifier = Modifier.weight(weight = 1f))
        drawableResourceList.forEach {
            Image(
                painter = painterResource(id = drawableResourceList[it]),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun TextTopBar(
    text: String,
    modifier: Modifier = Modifier,
    onClickLeftImage: () -> Unit = {},
    onClickRightText: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 56.dp)
            .padding(all = 8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_40_back),
            contentDescription = null,
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .clickable(onClick = onClickLeftImage)
        )
        DDDText(
            text = text,
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
                .clickable(onClick = onClickRightText),
            color = DDD_400,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500
        )
    }
}

@Composable
private fun DDDBasicTopBar(
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    onClickLeftImage: () -> Unit = {},
    onClickRightImage: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_40_back),
            contentDescription = null,
            modifier = Modifier
                .clickable(onClick = onClickLeftImage),
            colorFilter = colorFilter
        )

        Spacer(modifier = Modifier.weight(1F))

        Image(
            painter = painterResource(id = R.drawable.ic_24_info),
            contentDescription = null,
            modifier = Modifier
                .clickable(onClick = onClickRightImage)
        )
    }
}

enum class TopBarType {
    LEFT_IMAGE,
    LEFT_RIGHT_IMAGE,
    LEFT_IMAGE_RIGHT_TEXT,
    LEFT_IMAGE_CENTER,
    LEFT_IMAGE_WEIGHT_RIGHT_IMAGE,
    IMAGE
}

@Preview(name = "뒤로가기 버튼이 없는 NonBackButtonTopBar")
@Composable
private fun P1() {
    NonBackButtonTopBar(
        drawableResourceList = listOf(
            R.drawable.ic_40_back,
            R.drawable.ic_40_qr,
            R.drawable.ic_40_logo
        )
    )
}

@Preview(name = "텍스트가 있는 TextTopBar")
@Composable
private fun P2() {
    TextTopBar(text = "건너뛰기")
}

@Preview(name = "오른쪽 이미지가 있는 DDDTopBar")
@Composable
private fun P3() {
    DDDTopBar(isVisibleRightImage = true)
}

@Preview(name = "오른쪽 이미지가 없는 DDDTopBar")
@Composable
private fun P4() {
    DDDTopBar(isVisibleRightImage = false)
}