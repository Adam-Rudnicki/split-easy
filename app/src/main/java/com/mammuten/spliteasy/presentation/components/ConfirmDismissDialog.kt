package com.mammuten.spliteasy.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ConfirmDismissDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = { Icon(imageVector = icon, contentDescription = "Icon") },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = { onConfirmation() },
                content = { Text(text = "Confirm") }
            )
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() },
                content = { Text(text = "Dismiss") }
            )
        }
    )
}

@Preview
@Composable
fun ConfirmDismissDialogPreview() {
    ConfirmDismissDialog(
        onDismissRequest = {},
        onConfirmation = {},
        dialogTitle = "Delete Bill",
        dialogText = "Are you sure you want to delete this bill?",
        icon = Icons.Default.Delete
    )
}