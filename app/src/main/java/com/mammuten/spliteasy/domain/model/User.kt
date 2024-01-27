package com.mammuten.spliteasy.domain.model

data class User (
    val id: Int? = null,
    val name: String,
    val surname: String? = null,
    val nick: String? = null
) {
    companion object {
        val IS_NAME_REQUIRED = true
        val MIN_NAME_LEN = 2
        val MAX_NAME_LEN = 50

        val IS_SURNAME_REQUIRED = false
        val MIN_SURNAME_LEN = 2
        val MAX_SURNAME_LEN = 50

        val IS_NICK_REQUIRED = false
        val MIN_NICK_LEN = 2
        val MAX_NICK_LEN = 50
    }
}