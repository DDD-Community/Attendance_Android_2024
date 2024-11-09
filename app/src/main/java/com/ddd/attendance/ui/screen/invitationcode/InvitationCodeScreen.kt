package com.ddd.attendance.ui.screen.invitationcode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ddd.attendance.R
import com.ddd.attendance.ui.LoginProcessViewModel
import com.ddd.attendance.ui.ScreenName
import com.ddd.attendance.ui.component.DDDButton
import com.ddd.attendance.ui.component.DDDText
import com.ddd.attendance.ui.component.DDDTopBar
import com.ddd.attendance.ui.component.TopBarType
import com.ddd.attendance.ui.theme.DDD_BLACK
import com.ddd.attendance.ui.theme.DDD_BLUE
import com.ddd.attendance.ui.theme.DDD_BLUE_100
import com.ddd.attendance.ui.theme.DDD_ERROR
import com.ddd.attendance.ui.theme.DDD_Neutral_RED
import com.ddd.attendance.ui.theme.DDD_WHITE

@Composable
fun InvitationCodeScreen(
    navController: NavController,
    viewModel: LoginProcessViewModel
) {
    Content(
        onClickBackButton = {
            navController.popBackStack()
        },
        onClickSignup = {
            viewModel.onClickSignup()
            navController.navigate(route = ScreenName.NAME.name)
        }
    )
}

@Composable
private fun Content(
    onClickBackButton: () -> Unit,
    onClickSignup: () -> Unit
) {
    var value by remember { mutableStateOf(TextFieldValue("")) }
    val onValueChanged: (TextFieldValue) -> Unit = {
        // 4글자 이상 입력하는 경우 무시
        value = if (it.text.length > 4) {
            value
        } else {
            it
        }
    }

    val isFilled by remember { derivedStateOf { value.text.length >= 4 } }

    var isWrong by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DDD_BLACK)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DDDTopBar(
            type = TopBarType.LEFT_IMAGE,
            onClickLeftImage = onClickBackButton
        )
        Spacer(modifier = Modifier.height(height = 54.dp))
        DDDText(
            text = "초대코드를 입력해주세요",
            modifier = Modifier
                .padding(top = 54.dp),
            color = DDD_WHITE,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        DDDText(
            text = "가입을 위해 신규 기수 초대 코드가 필요합니다. \n받으신 4자리 초대 코드를 입력해 주세요.",
            color = DDD_WHITE,
            modifier = Modifier
                .padding(horizontal = 58.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))
        DDDInput(
            value,
            onValueChanged,
            isWrong,
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DDD_BLUE,
                disabledContainerColor = DDD_BLUE_100
            ),
            enabled = isFilled,
            contentPadding = PaddingValues(vertical = 12.dp),
            onClick = onClickSignup
        ) {
            Text(
                "다음",
            )
        }
    }
}

@Composable
private fun InvalidCodeView(modifier: Modifier = Modifier) {
    DDDText(
        text = "코드가 유효하지 않아요",
        modifier = modifier,
        color = DDD_ERROR,
        fontSize = 16.sp,
        fontWeight = FontWeight.W500
    )
}

@Composable
private fun DDDInput(
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    isWrong: Boolean,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(4) { index ->
                val digit = value.text.getOrNull(index)
                if (digit != null) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                color = if (!isWrong) DDD_BLUE_100 else DDD_Neutral_RED,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .then(
                                if (isWrong) Modifier.border(
                                    width = 2.dp,
                                    color = DDD_ERROR,
                                    shape = RoundedCornerShape(16.dp)
                                ) else Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = digit.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp,

                            )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .border(
                                width = 2.dp,
                                color = DDD_BLUE_100,
                                shape = RoundedCornerShape(16.dp)
                            ),
                    )
                }
            }
        }
    }
}

@Preview(name = "Content")
@Composable
private fun P1() {
    Content(
        onClickSignup = {},
        onClickBackButton = {}
    )
}

@Preview(name = "코드가 일치하지 않을 경우")
@Composable
private fun P2() {
    InvalidCodeView()
}

@Preview(name = "비밀번호 입력 칸")
@Composable
private fun P3() {
    DDDInput(
        value = TextFieldValue("0123"),
        onValueChanged = {},
        isWrong = false
    )
}