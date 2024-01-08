package com.mammuten.spliteasy.domain.model

import java.util.Date

data class Bill(
    val id: Int? = null,
    val groupId: Int,
    val name: String,
    val description: String? = null,
    val amount: Double? = null,
    val date: Date? = null
) {
    companion object {
        val IS_NAME_REQUIRED = true
        val MIN_NAME_LEN = 2
        val MAX_NAME_LEN = 50

        val IS_DESC_REQUIRED = false
        val MIN_DESC_LEN = 2
        val MAX_DESC_LEN = 200

        val IS_AMOUNT_REQUIRED = false
        val MAX_AMOUNT = 1000000.0

        val IS_DATE_REQUIRED = false
    }
}
