package com.ddd.attendance.feature.admin.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ddd.attendance.R
import com.ddd.attendance.core.ui.theme.DDD_BLACK
import com.ddd.attendance.core.ui.theme.DDD_WHITE
import com.ddd.attendance.core.utils.noRippleClickable
import com.ddd.attendance.feature.admin.AdminViewModel

@Composable
fun AdminScreen(
    navController: NavController,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val menu by viewModel.menu.collectAsStateWithLifecycle()

    Content(
        menu = menu,
        changeMenu = viewModel::setMenu,
        onClickBackButton = {
            navController.popBackStack()
        },
    )
}

@Composable
private fun Content(
    menu: String,
    changeMenu: (String) -> Unit,
    onClickBackButton: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DDD_BLACK)
    ) {
        Header(
            menu = menu,
            changeMenu = changeMenu,
        )
        when (menu) {
            "출석" -> {
                AttendanceScreen()
            }

            "일정" -> {

            }

            else -> {}
        }
    }
}


@Composable
private fun Header(
    modifier: Modifier = Modifier,
    menu: String,
    changeMenu: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        var expanded by remember { mutableStateOf(false) }

        Text(
            text = menu,
            modifier = Modifier.noRippleClickable {
                expanded = true
            },
            color = DDD_WHITE,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("출석") },
                onClick = {
                    changeMenu("출석")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("일정") },
                onClick = {
                    changeMenu("일정")
                    expanded = false
                }
            )
        }

        Row {
            Image(
                modifier = Modifier.noRippleClickable(onClick = /*onPressQrcode*/{}),
                painter = painterResource(R.drawable.ic_36_qr_code),
                contentDescription = "Qr Icon"
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                modifier = Modifier.noRippleClickable(onClick = /*onPressMyInfo*/{}),
                painter = painterResource(R.drawable.ic_36_my_info),
                contentDescription = "Info Icon"
            )
        }
    }
}


@Preview
@Composable
private fun P1() {
    Content(
        menu = "출석",
        changeMenu = {},
        onClickBackButton = {},
    )
}