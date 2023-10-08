package com.mammuten.spliteasy.domain.model

data class Bill(
    val id: Int,
    val name: String,
    val group: Group,
    val amount: Double,
//    val contributions: List<Contribution>,
//    val participants: List<User>
)