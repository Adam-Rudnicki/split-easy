package com.mammuten.spliteasy.domain.model

data class Contribution(
    val bill: Bill,
    val user: User,
    val amountPaid: Double,
    val amountOwed: Double
)