package com.mammuten.spliteasy.presentation.components

import java.util.Date

sealed interface InvalidInputError {
    data object Required : InvalidInputError
    data class TooShortText(val minLength: Int) : InvalidInputError
    data class TooLongText(val maxLength: Int) : InvalidInputError
    data class TooBigDecimal(val maxValue: Double) : InvalidInputError
    data class TooSmallDecimal(val minValue: Double) : InvalidInputError

    companion object {
        fun checkText(
            text: String,
            isRequired: Boolean,
            minLength: Int,
            maxLength: Int,
        ): InvalidInputError? {
            val trimmed = text.trim()
            return when {
                !isRequired && trimmed.isEmpty() -> null
                isRequired && trimmed.isEmpty() -> Required
                trimmed.length < minLength -> TooShortText(minLength)
                trimmed.length > maxLength -> TooLongText(maxLength)
                else -> null
            }
        }

        fun checkDecimal(
            decimal: Double?,
            isRequired: Boolean,
            maxValue: Double,
            minValue: Double = 0.0
        ): InvalidInputError? {
            return when {
                decimal == null && !isRequired -> null
                decimal == null && isRequired -> Required
                decimal != null && decimal > maxValue -> TooBigDecimal(maxValue)
                decimal != null && decimal < minValue -> TooSmallDecimal(minValue)
                else -> null
            }
        }

        fun checkDate(
            date: Date?,
            isRequired: Boolean,
        ): InvalidInputError? {
            return when {
                date == null && !isRequired -> null
                date == null && isRequired -> Required
                else -> null
            }
        }
    }
}

