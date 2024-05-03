package com.mammuten.spliteasy.presentation.components.input_state

import com.mammuten.spliteasy.presentation.components.InvalidInputError
import java.util.Date

data class DateState(
    val value: Date? = null,
    val error: InvalidInputError? = null,
)