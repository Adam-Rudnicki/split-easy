package com.mammuten.spliteasy.domain.usecase.bill

import com.mammuten.spliteasy.data.repo.BillRepo
import com.mammuten.spliteasy.data.repo.ContributionRepo
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.util.order.BillOrder
import com.mammuten.spliteasy.domain.util.order.ContributionOrder
import com.mammuten.spliteasy.domain.util.order.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//class GetBillsByGroupIdUseCase(
//    private val billRepo: BillRepo
//) {
//    operator fun invoke(
//        groupId: Int,
//        billOrder: BillOrder = BillOrder.DateAscending
//    ): Flow<List<Bill>> {
//        return billRepo.getBillsByGroupId(groupId).map { bills ->
//            when (billOrder) {
//                is BillOrder.Name -> {
//                    when (billOrder.orderType) {
//                        is OrderType.Ascending -> bills.sortedBy { it.name.lowercase() }
//                        is OrderType.Descending -> bills.sortedByDescending { it.name.lowercase() }
//                    }
//                }
//
//                is BillOrder.Amount -> {
//                    when (billOrder.orderType) {
//                        is OrderType.Ascending -> bills.sortedWith(compareBy(nullsLast()) { it.amount })
//                        is OrderType.Descending -> bills.sortedWith(compareByDescending(nullsFirst()) { it.amount })
//                    }
//                }
//
//                is BillOrder.Date -> {
//                    when (billOrder.orderType) {
//                        is OrderType.Ascending -> bills.sortedWith(compareBy(nullsLast()) { it.date })
//                        is OrderType.Descending -> bills.sortedWith(compareByDescending(nullsFirst()) { it.date })
//                    }
//                }
//            }
//        }
//    }
//}

class GetBillsByGroupIdUseCase(
    private val billRepo: BillRepo
) {
    operator fun invoke(
        groupId: Int,
        billOrder: BillOrder = BillOrder.DateAscending
    ): Flow<List<Bill>>{
        return billRepo.getBillsByGroupId(groupId).map { bills ->
            when (billOrder) {
                is BillOrder.NameAscending -> bills.sortedBy { it.name.lowercase() }
                is BillOrder.NameDescending -> bills.sortedByDescending { it.name.lowercase() }
                is BillOrder.DateAscending -> bills.sortedBy { it.date }
                is BillOrder.DateDescending -> bills.sortedByDescending { it.date }
                is BillOrder.AmountAscending -> bills.sortedBy { it.amount }
                is BillOrder.AmountDescending -> bills.sortedByDescending { it.amount }
            }
        }
    }
}