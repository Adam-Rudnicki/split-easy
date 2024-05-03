package com.mammuten.spliteasy.domain.model

data class User (
    val id: Int? = null,
    val name: String,
    val surname: String? = null,
    val nick: String? = null
) {
    companion object {
        const val IS_NAME_REQUIRED = true
        const val MIN_NAME_LEN = 2
        const val MAX_NAME_LEN = 50

        const val IS_SURNAME_REQUIRED = false
        const val MIN_SURNAME_LEN = 2
        const val MAX_SURNAME_LEN = 50

        const val IS_NICK_REQUIRED = false
        const val MIN_NICK_LEN = 2
        const val MAX_NICK_LEN = 50
    }
}