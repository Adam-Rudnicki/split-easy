package com.mammuten.spliteasy.domain.model

data class Group(
    val id: Int,
    val name: String,
    val bills: List<Bill>,
    val users: List<User>
)