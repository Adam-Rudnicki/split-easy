package com.mammuten.spliteasy.domain.util.order

sealed class UserOrder(val orderType: OrderType) {
    class Name(orderType: OrderType) : UserOrder(orderType)
    class Surname(orderType: OrderType) : UserOrder(orderType)
    class Nick(orderType: OrderType) : UserOrder(orderType)

    fun copy(orderType: OrderType): UserOrder {
        return when (this) {
            is Name -> Name(orderType)
            is Surname -> Surname(orderType)
            is Nick -> Nick(orderType)
        }
    }
}