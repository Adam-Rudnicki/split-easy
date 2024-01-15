package com.mammuten.spliteasy.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    date: Date,
    onConfirm: (Date) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(date.time)

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    val selectedDateMillis = datePickerState.selectedDateMillis
                    if (selectedDateMillis != null) {
                        val formattedDate = Date(selectedDateMillis)
                        onConfirm(formattedDate)
                    }
                },
                content = { Text(text = "OK") }
            )
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                content = { Text(text = "Cancel") }
            )
        },
        content = { DatePicker(state = datePickerState) }
    )
}

@Preview
@Composable
fun MyDatePickerPreview() {
    MyDatePicker(
        date = Date(),
        onConfirm = {},
        onDismiss = {}
    )
}
