package com.mammuten.spliteasy.domain.util.order

//sealed class GroupOrder(val orderType: OrderType) {
//    class Name(orderType: OrderType) : GroupOrder(orderType)
//    class Date(orderType: OrderType) : GroupOrder(orderType)
//
//    fun copy(orderType: OrderType): GroupOrder {
//        return when (this) {
//            is Name -> Name(orderType)
//            is Date -> Date(orderType)
//        }
//    }
//}

sealed class GroupOrder {
    data object NameAscending : GroupOrder()
    data object NameDescending : GroupOrder()
    data object DateAscending : GroupOrder()
    data object DateDescending : GroupOrder()
}