package com.mammuten.spliteasy.domain.util

sealed class ContributionOrder(val orderType: OrderType) {
    class AmountPaid(orderType: OrderType) : ContributionOrder(orderType)
    class AmountOwed(orderType: OrderType) : ContributionOrder(orderType)

    fun copy(orderType: OrderType): ContributionOrder {
        return when (this) {
            is AmountPaid -> AmountPaid(orderType)
            is AmountOwed -> AmountOwed(orderType)
        }
    }
}