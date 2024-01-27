package com.mammuten.spliteasy.domain.util.order

sealed interface MemberOrder {
    data object NameAsc : MemberOrder
    data object NameDesc : MemberOrder
}