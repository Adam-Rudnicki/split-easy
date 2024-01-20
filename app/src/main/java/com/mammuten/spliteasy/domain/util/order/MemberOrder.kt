package com.mammuten.spliteasy.domain.util.order

sealed class MemberOrder(val orderType: OrderType) {
    class Name(orderType: OrderType) : MemberOrder(orderType)

    fun copy(orderType: OrderType): MemberOrder {
        return when (this) {
            is Name -> Name(orderType)
        }
    }
}