package com.mammuten.spliteasy.domain.model

data class User (
    val id: Int? = null,
    val name: String,
    val surname: String? = null,
    val nick: String? = null,
    val phone: String? = null,
    val description: String? = null
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

        val IS_PHONE_REQUIRED = false
        val MIN_PHONE_LEN = 0
        val MAX_PHONE_LEN = 20

        val IS_DESC_REQUIRED = false
        val MIN_DESC_LEN = 2
        val MAX_DESC_LEN = 200
    }
}