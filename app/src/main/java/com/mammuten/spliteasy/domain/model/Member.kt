package com.mammuten.spliteasy.domain.model

data class Member(
    val id: Int? = null,
    val groupId: Int,
    val name: String,
) {
    companion object {
        val IS_NAME_REQUIRED = true
        val MIN_NAME_LEN = 2
        val MAX_NAME_LEN = 50
    }
}