package com.mammuten.spliteasy.domain.util.order

sealed class BillOrder(val orderType: OrderType) {
    class Name(orderType: OrderType) : BillOrder(orderType)
    class Amount(orderType: OrderType) : BillOrder(orderType)
    class Date(orderType: OrderType) : BillOrder(orderType)

    fun copy(orderType: OrderType): BillOrder {
        return when (this) {
            is Name -> Name(orderType)
            is Amount -> Amount(orderType)
            is Date -> Date(orderType)
        }
    }
}