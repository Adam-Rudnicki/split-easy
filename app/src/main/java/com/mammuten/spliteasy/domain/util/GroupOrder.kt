package com.mammuten.spliteasy.domain.util

sealed class GroupOrder(val orderType: OrderType) {
    class Name(orderType: OrderType) : GroupOrder(orderType)
    class Date(orderType: OrderType) : GroupOrder(orderType)

    fun copy(orderType: OrderType): GroupOrder {
        return when (this) {
            is Name -> Name(orderType)
            is Date -> Date(orderType)
        }
    }
}