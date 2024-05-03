package com.mammuten.spliteasy.domain.model

data class Member(
    val id: Int? = null,
    val groupId: Int,
    val userId: Int? = null,
    val name: String,
) {
    companion object {
        const val IS_NAME_REQUIRED = true
        const val MIN_NAME_LEN = 2
        const val MAX_NAME_LEN = 50
    }
}