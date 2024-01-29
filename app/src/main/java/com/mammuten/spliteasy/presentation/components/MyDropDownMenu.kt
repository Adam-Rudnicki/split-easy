package com.mammuten.spliteasy.presentation.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MyDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: List<Pair<String, () -> Unit>>
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        content = {
            items.forEach { (text, onClick) ->
                DropdownMenuItem(
                    onClick = {
                        onClick()
                        onDismissRequest()
                    },
                    text = { Text(text) }
                )
            }
        }
    )
}
