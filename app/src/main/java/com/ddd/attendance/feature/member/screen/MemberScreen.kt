package com.ddd.attendance.feature.member.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ddd.attendance.R
import com.ddd.attendance.core.designsystem.AttendanceStatusRow
import com.ddd.attendance.core.designsystem.DDDMemberSituation
import com.ddd.attendance.core.designsystem.DDDText
import com.ddd.attendance.core.model.Schedule
import com.ddd.attendance.core.ui.component.BottomSheetQrScaffold
import com.ddd.attendance.core.ui.theme.DDD_BLACK
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_BLUE_20
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_20
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_GRAY_90
import com.ddd.attendance.core.ui.theme.DDD_WHITE
import com.ddd.attendance.feature.main.MainViewModel
import com.ddd.attendance.feature.member.MemberViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberScreen(
    navController: NavController,
    viewModel: MemberViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val permissionRequestedState = viewModel.isPermissionRequested.collectAsState()
    val attendanceUiState = viewModel.attendanceUiState.collectAsStateWithLifecycle()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            density = LocalDensity.current,
            skipHiddenState = false,
            skipPartiallyExpanded = false,
            initialValue = SheetValue.Hidden
        )
    )

    val isPermissionGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // QR 바텀 시트 열기/닫기
    val bottomSheetStateChange = {
        coroutineScope.launch {
            val sheetState = scaffoldState.bottomSheetState
            if (sheetState.isVisible) {
                sheetState.hide()
            } else {
                sheetState.expand()
            }
        }
    }

    // 뒤로가기/QR 바텀 시트 닫기
    val handleBackButton = {
        coroutineScope.launch {
            val sheetState = scaffoldState.bottomSheetState
            if (sheetState.isVisible) sheetState.hide() else navController.popBackStack()
        }
    }

    BottomSheetQrScaffold(
        scaffoldState = scaffoldState,
        onCloseImageClicked = {
            handleBackButton()
        },
        onQrCodeScanned = { qrData ->

        },
        bodyContent = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Content(
                    onPressMyPage = {},
                    onPressQrcode = {
                        if (isPermissionGranted.value) {
                            bottomSheetStateChange()
                        } else {
                            viewModel.permissionRequested(true)
                        }
                    },
                    onClickBackButton = {
                        handleBackButton()
                    }
                )

                if (permissionRequestedState.value) {
                    RequestCameraPermission(
                        context = context,
                        onPermissionGranted = {
                            viewModel.permissionRequested(false)
                            isPermissionGranted.value = true
                            bottomSheetStateChange()
                        },
                        onPermissionDenied = {
                            viewModel.permissionRequested(false)
                            isPermissionGranted.value = false
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun Content(
    onPressMyPage: () -> Unit,
    onPressQrcode: () -> Unit,
    onClickBackButton: () -> Unit
) {
    val schedules = getSchedules()

    BackHandler { onClickBackButton() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DDD_BLACK)
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Spacer(Modifier.height(36.dp))
                HeaderSection(onPressMyPage, onPressQrcode)
            }
            item {
                Spacer(Modifier.height(20.dp))
                BodySection()
            }
            items(schedules) { schedule ->
                ScheduleItem(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    schedule = schedule
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun HeaderSection(
    onPressMyPage: () -> Unit,
    onPressQrcode: () -> Unit
) {
    AttendanceStatusRow(
        modifier = Modifier.padding(start = 16.dp, end = 24.dp),
        onPressQrcode = onPressQrcode,
        onPressMyPage = onPressMyPage
    )
}

@Composable
private fun BodySection() {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        DDDText(
            text = stringResource(R.string.member_attendance_status, "김디디"),
            color = DDD_WHITE,
            textStyle = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        DDDText(
            text = stringResource(R.string.member_activity_period, "2025.03.12 ~ 2025.08.12"),
            color = DDD_NEUTRAL_GRAY_20,
            textStyle = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Normal
        )

        Spacer(Modifier.height(8.dp))

        DDDMemberSituation(attendanceCount = 8, tardyCount = 2, absentCount = 1)

        Spacer(Modifier.height(56.dp))

        DDDText(
            text = stringResource(R.string.member_th_schedule, "12"),
            color = DDD_WHITE,
            textStyle = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun ScheduleItem(
    modifier: Modifier = Modifier,
    schedule: Schedule
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(DDD_NEUTRAL_GRAY_90)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ScheduleDateBox(schedule)
            Spacer(Modifier.width(12.dp))
            ScheduleDetails(schedule)
        }
    }
}

@Composable
private fun ScheduleDateBox(schedule: Schedule) {
    Box(
        modifier = Modifier
            .size(54.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(DDD_NEUTRAL_BLUE_20),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            DDDText(
                text = schedule.month,
                color = DDD_BLACK,
                textStyle = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(4.dp))
            DDDText(
                text = schedule.day,
                color = DDD_BLACK,
                textStyle = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ScheduleDetails(schedule: Schedule) {
    Column(verticalArrangement = Arrangement.Center) {
        DDDText(
            text = schedule.title,
            color = DDD_WHITE,
            textStyle = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        DDDText(
            text = schedule.content,
            color = DDD_NEUTRAL_GRAY_20,
            textStyle = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun RequestCameraPermission(
    context: Context,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted()
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}

private fun getSchedules(): List<Schedule> = listOf(
    Schedule("6월", "11", "오리엔테이션", "커리큘럼에 대한 설명 문구 작성"),
    Schedule("6월", "22", "부스팅 데이 1", "커리큘럼에 대한 설명 문구 작성"),
    Schedule("7월", "06", "St. Patrick's Day", "Irish cultural celebration"),
    Schedule("6월", "25", "April Fools' Day", "Day for jokes and pranks"),
    Schedule("9월", "21", "부스팅 데이 2", "설명 문구 작성"),
    Schedule("10월", "11", "직군 세션", "놀자 놀자")
)