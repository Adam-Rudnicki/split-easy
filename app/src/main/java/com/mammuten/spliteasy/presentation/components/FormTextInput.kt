package com.mammuten.spliteasy.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mammuten.spliteasy.presentation.util.RegexUtil

@Composable
fun FormTextInput(
    modifier: Modifier = Modifier,
    label: String? = null,
    text: String,
    error: InvalidInputError?,
    onValueChange: (String) -> Unit,
    isRequired: Boolean,
    singleLine: Boolean = true,
    isEnabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        modifier = modifier,
        label = {
            label?.let {
                Text(
                    text = it + if (!isRequired) " (optional)" else "",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        },
        value = text,
        onValueChange = {
            if (isValidInput(it, keyboardType)) {
                onValueChange(it)
            }
        },
        singleLine = singleLine,
        enabled = isEnabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(
                    onClick = { onValueChange("") },
                    content = {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear"
                        )
                    }
                )
            }
        },
        supportingText = {
            error?.let {
                when (it) {
                    is InvalidInputError.Required -> Text(text = "This field is required")
                    is InvalidInputError.TooShortText -> Text(text = "Text is too short (minimum length: ${it.minLength})")
                    is InvalidInputError.TooLongText -> Text(text = "Text is too long (maximum length: ${it.maxLength})")
                    is InvalidInputError.TooBigDecimal -> Text(text = "Amount is too big (maximum: ${it.maxValue})")
                    is InvalidInputError.TooSmallDecimal -> Text(text = "Amount is too small (minimum: ${it.minValue})")
                }
            }
        },
        isError = error != null
    )
}

private fun isValidInput(input: String, keyboardType: KeyboardType): Boolean {
    return when (keyboardType) {
        KeyboardType.Text -> true
        KeyboardType.Decimal -> input.matches(RegexUtil.inputTwoDecimalPlacesRegex)
        KeyboardType.Phone -> input.matches(RegexUtil.phoneRegex)
        else -> false
    }
}

@Preview
@Composable
fun FormTextInputPreview() {
    FormTextInput(
        label = "Name",
        text = "",
        error = null,
        onValueChange = {},
        isRequired = false
    )
}