package com.mammuten.spliteasy.domain.usecase.bill

import com.mammuten.spliteasy.data.repo.BillRepo
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.util.order.BillOrder
import com.mammuten.spliteasy.domain.util.order.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBillsByGroupIdUseCase(
    private val billRepo: BillRepo
) {
    operator fun invoke(
        groupId: Int,
        billOrder: BillOrder = BillOrder.Date(OrderType.Descending)
    ): Flow<List<Bill>> {
        return billRepo.getBillsByGroupId(groupId).map { bills ->
            when (billOrder) {
                is BillOrder.Name -> {
                    when (billOrder.orderType) {
                        is OrderType.Ascending -> bills.sortedBy { it.name.lowercase() }
                        is OrderType.Descending -> bills.sortedByDescending { it.name.lowercase() }
                    }
                }

                is BillOrder.Amount -> {
                    when (billOrder.orderType) {
                        is OrderType.Ascending -> bills.sortedWith(compareBy(nullsLast()) { it.amount })
                        is OrderType.Descending -> bills.sortedWith(compareByDescending(nullsFirst()) { it.amount })
                    }
                }

                is BillOrder.Date -> {
                    when (billOrder.orderType) {
                        is OrderType.Ascending -> bills.sortedWith(compareBy(nullsLast()) { it.date })
                        is OrderType.Descending -> bills.sortedWith(compareByDescending(nullsFirst()) { it.date })
                    }
                }
            }
        }
    }
}