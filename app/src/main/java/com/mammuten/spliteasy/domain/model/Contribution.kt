package com.mammuten.spliteasy.domain.model

data class Contribution(
    val billId: Int,
    val memberId: Int,
    val amountPaid: Double,
    val amountOwed: Double
) {
    companion object {
        val IS_AMOUNT_REQUIRED = true
        val MAX_AMOUNT = 1000000.0
    }
}
