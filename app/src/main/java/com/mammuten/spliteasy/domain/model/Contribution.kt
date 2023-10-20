package com.mammuten.spliteasy.domain.model

data class Contribution(
    val billId: Int,
    val memberId: Int,
    val amountPaid: Double,
    val amountOwed: Double
)