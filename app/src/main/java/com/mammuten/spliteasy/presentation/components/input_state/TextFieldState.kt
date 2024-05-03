package com.mammuten.spliteasy.presentation.components.input_state

import com.mammuten.spliteasy.presentation.components.InvalidInputError

data class TextFieldState(
    val value: String = "",
    val error: InvalidInputError? = null,
)