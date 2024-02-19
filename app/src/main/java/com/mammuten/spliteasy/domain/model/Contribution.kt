package com.mammuten.spliteasy.domain.model

data class Contribution(
    val billId: Int,
    val memberId: Int,
    val amountPaid: Int,
    val amountOwed: Int
) {
    companion object {
        const val MAX_AMOUNT = 100000000
    }
}
