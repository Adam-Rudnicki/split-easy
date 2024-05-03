package com.mammuten.spliteasy.domain.model

import java.util.Date

data class Bill(
    val id: Int? = null,
    val groupId: Int,
    val name: String,
    val description: String? = null,
    val date: Date? = null
) {
    companion object {
        const val IS_NAME_REQUIRED = true
        const val MIN_NAME_LEN = 2
        const val MAX_NAME_LEN = 50

        const val IS_DESC_REQUIRED = false
        const val MIN_DESC_LEN = 2
        const val MAX_DESC_LEN = 200

        const val IS_DATE_REQUIRED = false
    }
}
