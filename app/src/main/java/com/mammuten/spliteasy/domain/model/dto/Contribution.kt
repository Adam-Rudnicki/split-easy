package com.mammuten.spliteasy.domain.model.dto

data class Contribution(
    val billId: Int,
    val memberId: Int,
    val amountPaid: Double,
    val amountOwed: Double
)