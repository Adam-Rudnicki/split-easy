package com.mammuten.spliteasy.presentation.components

sealed interface InvalidInputError {
    data object Required : InvalidInputError
    data class TooShort(val minLength: Int) : InvalidInputError
    data class TooLong(val maxLength: Int) : InvalidInputError

    companion object {
        fun checkInput(
            text: String,
            isRequired: Boolean,
            minLength: Int,
            maxLength: Int,
        ): InvalidInputError? {
            val trimmed = text.trim()
            return when {
                !isRequired && trimmed.isEmpty() -> null
                isRequired && trimmed.isEmpty() -> Required
                trimmed.length < minLength -> TooShort(minLength)
                trimmed.length > maxLength -> TooLong(maxLength)
                else -> null
            }
        }
    }
}

