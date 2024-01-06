package com.mammuten.spliteasy.domain.usecase.bill

import com.mammuten.spliteasy.data.repo.BillRepo
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.util.BillOrder
import com.mammuten.spliteasy.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBillsByGroupIdUseCase(
    private val billRepo: BillRepo
) {
    operator fun invoke(
        groupId: Int,
        billOrder: BillOrder? = null
    ): Flow<List<Bill>> {
        return billRepo.getBillsByGroupId(groupId).map { bills ->
            billOrder?.let { order ->
                when (order) {
                    is BillOrder.Name -> {
                        when (order.orderType) {
                            is OrderType.Ascending -> bills.sortedBy { it.name.lowercase() }
                            is OrderType.Descending -> bills.sortedByDescending { it.name.lowercase() }
                        }
                    }

                    is BillOrder.Amount -> {
                        when (order.orderType) {
                            is OrderType.Ascending -> bills.sortedWith(nullsLast(compareBy { it.amount }))
                            is OrderType.Descending -> bills.sortedWith(
                                nullsLast(compareByDescending { it.amount })
                            )
                        }
                    }

                    is BillOrder.Date -> {
                        when (order.orderType) {
                            is OrderType.Ascending -> bills.sortedWith(nullsLast(compareBy { it.date }))
                            is OrderType.Descending -> bills.sortedWith(
                                nullsLast(compareByDescending { it.date })
                            )
                        }
                    }
                }
            } ?: bills
        }
    }
}