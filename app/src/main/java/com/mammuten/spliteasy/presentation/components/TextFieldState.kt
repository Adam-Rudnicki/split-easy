package com.mammuten.spliteasy.presentation.components

data class TextFieldState(
    val value: String = "",
    val error: InvalidInputError? = null,
)