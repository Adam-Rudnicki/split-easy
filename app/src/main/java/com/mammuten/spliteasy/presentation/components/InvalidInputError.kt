package com.mammuten.spliteasy.presentation.components

sealed class InvalidInputError {
    data object Required : InvalidInputError()
    data class TooShort(val minLength: Int) : InvalidInputError()
    data class TooLong(val maxLength: Int) : InvalidInputError()
}