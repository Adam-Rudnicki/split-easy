package com.mammuten.spliteasy.domain.util.order

sealed interface GroupOrder {
    data object NameAsc : GroupOrder
    data object NameDesc : GroupOrder

    data object DateAsc : GroupOrder
    data object DateDesc : GroupOrder
}