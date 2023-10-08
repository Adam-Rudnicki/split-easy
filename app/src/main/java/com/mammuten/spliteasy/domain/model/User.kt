package com.mammuten.spliteasy.domain.model

data class User(
    val id: Int,
    val username: String,
    val nickname: String?,
    val groups: List<Group>,
    val contributions: List<Contribution>
)