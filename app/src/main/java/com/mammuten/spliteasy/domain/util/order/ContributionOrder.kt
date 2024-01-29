package com.mammuten.spliteasy.domain.util.order

sealed interface ContributionOrder {
    data object AmountPaidAsc : ContributionOrder
    data object AmountPaidDesc : ContributionOrder

    data object AmountOwedAsc : ContributionOrder
    data object AmountOwedDesc : ContributionOrder
}
