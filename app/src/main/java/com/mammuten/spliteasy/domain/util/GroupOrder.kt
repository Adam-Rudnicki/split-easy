package com.mammuten.spliteasy.domain.util

sealed class GroupOrder(val orderType: OrderType) {
    class Name(orderType: OrderType) : GroupOrder(orderType)

}