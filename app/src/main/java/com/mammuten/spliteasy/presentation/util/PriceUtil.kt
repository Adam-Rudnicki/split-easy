package com.mammuten.spliteasy.presentation.util

import android.icu.math.BigDecimal

object PriceUtil {
    fun scaleAndMultiply(value: Double?): Int {
        return value?.let {
            BigDecimal(it).setScale(2, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal(100)).toInt()
        } ?: 0
    }

    fun scaleAndDivide(value: Int): String {
        return BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP)
            .divide(BigDecimal(100)).toString()
    }
}