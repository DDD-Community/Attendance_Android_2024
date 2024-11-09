package com.ddd.attendance.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.attendance.ui.theme.DDD_BLUE
import com.ddd.attendance.ui.theme.DDD_BLUE_100

@Composable
fun DDDNextButton(
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DDD_BLUE,
            disabledContainerColor = DDD_BLUE_100
        ),
        enabled = isEnabled,
        contentPadding = PaddingValues(vertical = 12.dp),
        onClick = onClick
    ) {
        Text(
            text,
        )
    }
}

@Preview(name = "활성화 된 버튼", widthDp = 300)
@Composable
private fun P1() {
    DDDNextButton(text = "다음")
}

@Preview(name = "비활성화 된 버튼", widthDp = 300)
@Composable
private fun P2() {
    DDDNextButton(text = "다음", isEnabled = false)
}