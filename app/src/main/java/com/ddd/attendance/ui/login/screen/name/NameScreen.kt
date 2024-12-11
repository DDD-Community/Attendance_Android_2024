package com.ddd.attendance.ui.login.screen.name

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ddd.attendance.R
import com.ddd.attendance.ui.login.LoginProcessViewModel
import com.ddd.attendance.ui.login.ScreenName
import com.ddd.attendance.ui.component.DDDNextButton
import com.ddd.attendance.ui.component.DDDProgressbar
import com.ddd.attendance.ui.component.DDDText
import com.ddd.attendance.ui.component.DDDTopBar
import com.ddd.attendance.ui.component.TopBarType
import com.ddd.attendance.ui.theme.DDD_BLACK
import com.ddd.attendance.ui.theme.DDD_BORDER_INACTIVE
import com.ddd.attendance.ui.theme.DDD_NEUTRAL_GRAY_20
import com.ddd.attendance.ui.theme.DDD_WHITE

@Composable
fun NameScreen(
    navController: NavController,
    viewModel: LoginProcessViewModel
) {
    Content(
        onClickBackButton = {
            navController.popBackStack()
        },
        onClickNext = {
            viewModel.onClickNextFromUserName()
            navController.navigate(route = ScreenName.JOB.name)
        }
    )
}

@Composable
private fun Content(
    onClickBackButton: () -> Unit,
    onClickNext: () -> Unit
) {
    var value by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val onValueChanged: (TextFieldValue) -> Unit = {
        value = it
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DDD_BLACK),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DDDTopBar(
            type = TopBarType.LEFT_IMAGE_CENTER,
            onClickLeftImage = onClickBackButton,
            center = {
                DDDProgressbar(
                    modifier = Modifier.then(it),
                    current = 1
                )
            }
        )
        Spacer(modifier = Modifier.height(height = 54.dp))
        DDDText(
            text = "이름이 어떻게 되시나요?",
            color = DDD_WHITE,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        DDDText(
            text = "가입하실 때 사용할 이름을 입력해 주세요",
            color = DDD_NEUTRAL_GRAY_20,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp
        )
        DDDInputText(
            value = value,
            onValueChanged = onValueChanged
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        DDDNextButton(
            text = "다음",
            modifier = Modifier.fillMaxWidth(),
            isEnabled = value.text.isNotEmpty(),
            onClick = onClickNext
        )
    }
}

@Composable
private fun DDDInputText(
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
) {
    BasicTextField(
        modifier = Modifier
            .padding(
                start = 32.dp,
                end = 32.dp,
                top = 54.dp
            ),
        value = value,
        onValueChange = onValueChanged,
        textStyle = TextStyle(
            color = DDD_WHITE,
            fontSize = 16.sp,
        ),
        maxLines = 1
    ) { innerTextField ->
        Box(modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(56.dp)
            .border(
                width = 1.dp,
                color = DDD_BORDER_INACTIVE,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Box(modifier = Modifier.align(Alignment.CenterStart)) {
                innerTextField()
            }
            if (value.text.isNotEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                        .clickable { onValueChanged(TextFieldValue("")) }
                    ,
                )
            }
        }
    }
}

@Preview(name = "Content")
@Composable
private fun P1() {
    Content(
        onClickBackButton = {},
        onClickNext = {}
    )
}