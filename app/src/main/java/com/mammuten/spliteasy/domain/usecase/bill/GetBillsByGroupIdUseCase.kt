package com.mammuten.spliteasy.domain.usecase.bill

import com.mammuten.spliteasy.data.repo.BillRepo
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.util.order.BillOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBillsByGroupIdUseCase(
    private val billRepo: BillRepo
) {
    operator fun invoke(
        groupId: Int,
        billOrder: BillOrder = BillOrder.DateAsc
    ): Flow<List<Bill>>{
        return billRepo.getBillsByGroupId(groupId).map { bills ->
            when (billOrder) {
                is BillOrder.NameAsc -> bills.sortedBy { it.name.lowercase() }
                is BillOrder.NameDesc -> bills.sortedByDescending { it.name.lowercase() }

                is BillOrder.DateAsc -> bills.sortedWith(compareBy(nullsLast()) { it.date })
                is BillOrder.DateDesc -> bills.sortedWith(compareByDescending(nullsFirst()) { it.date })

                is BillOrder.AmountAsc -> bills.sortedWith(compareBy(nullsLast()) { it.amount })
                is BillOrder.AmountDesc -> bills.sortedWith(compareByDescending(nullsFirst()) { it.amount })
            }
        }
    }
}