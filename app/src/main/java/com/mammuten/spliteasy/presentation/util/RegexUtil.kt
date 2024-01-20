package com.mammuten.spliteasy.presentation.util

object RegexUtil {
    val inputTwoDecimalPlacesRegex = Regex("^\\d*\\.?\\d{0,2}\$")
    val phoneRegex = Regex("^[0-9]{7,20}$")
}
