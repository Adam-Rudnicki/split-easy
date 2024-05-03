package com.mammuten.spliteasy.presentation.components

import java.util.Date

sealed interface InvalidInputError {
    data object Required : InvalidInputError
    data class TooShortText(val minLength: Int) : InvalidInputError
    data class TooLongText(val maxLength: Int) : InvalidInputError
    data class TooBigAmount(val maxAmount: Int) : InvalidInputError
    data class TooSmallAmount(val minAmount: Int) : InvalidInputError

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

        fun checkAmount(
            amount: Int?,
            isRequired: Boolean,
            maxAmount: Int,
            minAmount: Int = 0
        ): InvalidInputError? {
            return when {
                amount == null && !isRequired -> null
                amount == null && isRequired -> Required
                amount != null && amount > maxAmount -> TooBigAmount(maxAmount)
                amount != null && amount < minAmount -> TooSmallAmount(minAmount)
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

