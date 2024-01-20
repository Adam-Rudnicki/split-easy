package com.mammuten.spliteasy.domain.util.order

sealed class OrderType {
    data object Ascending: OrderType()
    data object Descending: OrderType()
}