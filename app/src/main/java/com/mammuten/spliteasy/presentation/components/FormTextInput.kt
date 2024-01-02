package com.mammuten.spliteasy.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FormTextInput(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    error: InvalidInputError?,
    onValueChange: (String) -> Unit,
    isRequired: Boolean,
    singleLine: Boolean = true
) {

    OutlinedTextField(
        modifier = modifier,
        label = {
            Text(
                text = label + if (!isRequired) " (optional)" else "",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        value = text,
        onValueChange = onValueChange,
        singleLine = singleLine,
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                }
            }
        },
        supportingText = {
            error?.let {
                when (it) {
                    is InvalidInputError.Required -> Text(text = "This field is required")
                    is InvalidInputError.TooShort -> Text(text = "Text is too short (minimum length: ${it.minLength})")
                    is InvalidInputError.TooLong -> Text(text = "Text is too long (maximum length: ${it.maxLength})")
                }
            }
        },
        isError = error != null
    )
}