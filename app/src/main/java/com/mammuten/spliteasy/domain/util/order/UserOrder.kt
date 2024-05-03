package com.mammuten.spliteasy.domain.util.order

sealed interface UserOrder {
    data object NameAsc : UserOrder
    data object NameDesc : UserOrder

    data object SurnameAsc : UserOrder
    data object SurnameDesc : UserOrder

    data object NickAsc : UserOrder
    data object NickDesc : UserOrder
}