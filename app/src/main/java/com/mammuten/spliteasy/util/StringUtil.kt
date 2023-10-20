package com.mammuten.spliteasy.util

import java.util.Locale

object StringUtil {
    fun splitString(input: String, delimiter: String): List<String> {
        return input.split(delimiter)
    }

    fun trimWhitespace(input: String): String {
        return input.trim()
    }

    fun stringToUpperCase(text: String): String {
        return text.uppercase(Locale.ROOT)
    }
}