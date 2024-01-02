package com.mammuten.spliteasy.domain.model

import java.util.Date

data class Group(
    val id: Int? = null,
    val name: String,
    val description: String? = null,
    val created: Date? = null
) {
    companion object {
        val IS_NAME_REQUIRED = true
        val MIN_NAME_LEN = 2
        val MAX_NAME_LEN = 50

        val IS_DESC_REQUIRED = false
        val MIN_DESC_LEN = 2
        val MAX_DESC_LEN = 200
    }
}