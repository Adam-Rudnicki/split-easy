package com.mammuten.spliteasy.util

import java.math.BigDecimal
import java.math.RoundingMode

object MathUtil {
    fun roundNumber(number: Double, decimalPlaces: Int = 2, roundingUp: Boolean = false): Double {
        val bd = BigDecimal(number)
        val roundingMode = if (roundingUp) RoundingMode.UP else RoundingMode.DOWN
        val roundedNumber = bd.setScale(decimalPlaces, roundingMode)
        return roundedNumber.toDouble()
    }

    fun calculatePercentage(
        value: Double,
        total: Double,
        decimalPlaces: Int = 2,
        roundingUp: Boolean = false
    ): Double {
        val percentage = value / total * 100
        return roundNumber(percentage, decimalPlaces, roundingUp)
    }
}