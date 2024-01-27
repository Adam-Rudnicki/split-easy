package com.mammuten.spliteasy.domain.util.order

sealed interface BillOrder {
    data object NameAsc : BillOrder
    data object NameDesc : BillOrder

    data object DateAsc : BillOrder
    data object DateDesc : BillOrder

    data object AmountAsc : BillOrder
    data object AmountDesc : BillOrder
}