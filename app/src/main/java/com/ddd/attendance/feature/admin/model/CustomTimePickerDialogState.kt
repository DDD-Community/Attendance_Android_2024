package com.ddd.attendance.feature.admin.model

// CustomTimePickerDialogState.kt
data class CustomTimePickerDialogState(
    var selectedHour: Int? = null,
    var selectedMinute: Int? = null,
    var isShowDialog: Boolean = false,
    val onClickConfirm: (hour: Int, minute: Int) -> Unit,
    val onClickCancel: () -> Unit,
)