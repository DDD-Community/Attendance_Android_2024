package com.ddd.attendance.feature.qr.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ddd.attendance.R
import com.ddd.attendance.core.designsystem.DDDText
import com.ddd.attendance.core.designsystem.DDDTopBar
import com.ddd.attendance.core.designsystem.TopBarType
import com.ddd.attendance.core.ui.theme.DDD_BLACK
import com.ddd.attendance.core.ui.theme.DDD_WHITE
import com.ddd.attendance.feature.main.screen.ScreenName
import com.ddd.attendance.feature.qr.QrViewModel

@Composable
fun QrImageScreen(
    navController: NavController,
    viewModel: QrViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(DDD_BLACK)
    ) {
        DDDTopBar(
            type = TopBarType.LEFT_IMAGE,
            onClickLeftImage = {
                navController.popBackStack()
            },
        )
        Content()
    }
}

@Composable
private fun Content() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DDDText(
            text = "QR 코드를 스캔해 주세요.",
            color = DDD_WHITE,
            textStyle = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        DDDText(
            text = "스캔 시 자동으로 출석이 인정됩니다.",
            color = DDD_WHITE,
            textStyle = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            modifier = Modifier.size(280.dp),
            painter = painterResource(R.drawable.qr_dummy),
            contentDescription = "qr dummy image"
        )
    }
}