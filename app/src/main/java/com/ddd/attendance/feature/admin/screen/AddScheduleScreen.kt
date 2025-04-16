package com.ddd.attendance.feature.admin.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.ddd.attendance.R
import com.ddd.attendance.core.designsystem.DDDText
import com.ddd.attendance.core.ui.theme.DDD_BLACK
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_BLUE_40
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_80
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_90
import com.ddd.attendance.core.ui.theme.DDD_TEXT_SECONDARY
import com.ddd.attendance.core.ui.theme.DDD_WHITE
import com.ddd.attendance.core.utils.noRippleClickable
import com.ddd.attendance.feature.admin.model.CustomTimePickerDialogState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun AddScheduleScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DDD_BLACK)
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Header { navController.popBackStack() }
            SessionDropDownCard()
            DateDropDownCard()
            TimeDropDownCard()
        }
        Button(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DDD_NEUTRAL_BLUE_40),
            contentPadding = PaddingValues(vertical = 16.dp),
            onClick = { /*TODO*/ }
        ) {
            DDDText(
                text = "완료",
                color = DDD_WHITE,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
private fun Header(onClickBack: () -> Unit) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ic_40_back),
                contentDescription = "뒤로 가기",
                modifier = Modifier.noRippleClickable { onClickBack() }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        DDDText(
            text = "일정 추가",
            color = DDD_WHITE,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
    }

}

@Composable
private fun SessionDropDownCard(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectItem by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        DropDownCard(
            modifier = Modifier,
            text = selectItem.ifEmpty { "추가할 세션을 선택하세요" },
            onClickIcon = { expanded = !expanded },
        )

        if (expanded) {
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(fraction = 0.8f),
                shape = RoundedCornerShape(12.dp),
                containerColor = DDD_TEXT_SECONDARY,
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = {
                        DDDText(
                            text = "정규 세션",
                            color = DDD_WHITE,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    },
                    onClick = {
                        selectItem = "정규 세션"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = {
                        DDDText(
                            text = "직군 세션",
                            color = DDD_WHITE,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    },
                    onClick = {
                        selectItem = "직군 세션"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = {
                        DDDText(
                            text = "커뮤니티 데이",
                            color = DDD_WHITE,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    },
                    onClick = {
                        selectItem = "커뮤니티 데이"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = {
                        DDDText(
                            text = "발표",
                            color = DDD_WHITE,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    },
                    onClick = {
                        selectItem = "발표"
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateDropDownCard(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val state = rememberDatePickerState()
    var date by remember { mutableStateOf("날짜를 선택하세요") }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        DropDownCard(
            text = date,
            onClickIcon = { expanded = !expanded }
        )
        if (expanded) {
            Spacer(modifier = Modifier.height(12.dp))
            DatePicker(state = state)
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    state.selectedDateMillis?.let {
                        date = Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                            .format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                    }
                    expanded = false
                }) {
                DDDText(
                    text = "완료",
                    color = DDD_WHITE,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
private fun TimeDropDownCard(modifier: Modifier = Modifier) {
    // 시간 변수들은 api 호출할 때 viewModel 쪽으로 옮길 예정
    var startExpanded by remember { mutableStateOf(false) }
    var startHour by remember { mutableIntStateOf(2) }
    var startMinute by remember { mutableIntStateOf(0) }
    var endExpanded by remember { mutableStateOf(false) }
    var endHour by remember { mutableIntStateOf(6) }
    var endMinute by remember { mutableIntStateOf(0) }

    val startTimePickerDialogState by remember {
        mutableStateOf(CustomTimePickerDialogState(
            selectedHour = startHour,
            selectedMinute = startMinute,
            onClickConfirm = { hour, minute ->
                startHour = hour
                startMinute = minute
                startExpanded = false
            },
            onClickCancel = {
                startExpanded = false
            }
        ))
    }

    val endTimePickerDialogState by remember {
        mutableStateOf(CustomTimePickerDialogState(
            selectedHour = endHour,
            selectedMinute = endMinute,
            onClickConfirm = { hour, minute ->
                endHour = hour
                endMinute = minute
                endExpanded = false
            },
            onClickCancel = {
                endExpanded = false
            }
        ))
    }

    Column {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = DDD_NEUTRAL_GRAY_90,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                DDDText(
                    text = "시작 시간",
                    color = DDD_WHITE,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                )
                DDDText(
                    modifier = Modifier
                        .background(color = DDD_BLACK, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .noRippleClickable {
                            startExpanded = true
                        },
                    text = String.format("%02d:%02d", startHour, startMinute),
                    color = DDD_WHITE,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = DDD_NEUTRAL_GRAY_80
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                DDDText(
                    text = "종료 시간",
                    color = DDD_WHITE,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                )
                DDDText(
                    modifier = Modifier
                        .background(color = DDD_BLACK, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .noRippleClickable { endExpanded = true },
                    text = String.format("%02d:%02d", endHour, endMinute),
                    color = DDD_WHITE,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                )
            }
        }
        if (startExpanded) {
            CustomTimePickerDialog(
                selectedHour = startTimePickerDialogState.selectedHour,
                selectedMinute = startTimePickerDialogState.selectedMinute,
                onClickCancel = startTimePickerDialogState.onClickCancel,
                onClickConfirm = startTimePickerDialogState.onClickConfirm,
            )
        }
        if (endExpanded) {
            CustomTimePickerDialog(
                selectedHour = endTimePickerDialogState.selectedHour,
                selectedMinute = endTimePickerDialogState.selectedMinute,
                onClickCancel = endTimePickerDialogState.onClickCancel,
                onClickConfirm = endTimePickerDialogState.onClickConfirm,
            )
        }
    }
}


@Composable
private fun DropDownCard(
    modifier: Modifier = Modifier,
    text: String,
    onClickIcon: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DDD_NEUTRAL_GRAY_90,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DDDText(
            text = text,
            color = DDD_WHITE,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
        )
        Icon(
            modifier = Modifier.noRippleClickable { onClickIcon() },
            painter = painterResource(id = R.drawable.ic_40_back),
            contentDescription = "dropdown",
            tint = DDD_WHITE
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    selectedHour: Int?,
    selectedMinute: Int?,
    onClickCancel: () -> Unit,
    onClickConfirm: (hour: Int, minute: Int) -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour ?: 0,
        initialMinute = selectedMinute ?: 0,
        is24Hour = false
    )

    Dialog(
        onDismissRequest = { onClickCancel() },
    ) {
        Card(
            shape = RoundedCornerShape(8.dp), // Card의 모든 꼭지점에 8.dp의 둥근 모서리 적용
        ) {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
                    .background(
                        color = DDD_WHITE,
                    )
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                DDDText(text = "시간을 선택해주세요.")

                Spacer(modifier = Modifier.width(15.dp))

                TimePicker(
                    state = timePickerState,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(onClick = {
                        onClickCancel()
                    }) {
                        DDDText(text = "취소")
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Button(onClick = {
                        val hour = timePickerState.hour
                        val minute = timePickerState.minute
                        onClickConfirm(hour, minute)
                    }) {
                        DDDText(text = "확인")
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun P1() {
    SessionDropDownCard()
}