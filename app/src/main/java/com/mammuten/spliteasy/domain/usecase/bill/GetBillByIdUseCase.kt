package com.mammuten.spliteasy.domain.usecase.bill

import com.mammuten.spliteasy.data.repo.BillRepo
import com.mammuten.spliteasy.domain.model.Bill
import kotlinx.coroutines.flow.Flow

class GetBillByIdUseCase(
    private val billRepo: BillRepo
) {
    operator fun invoke(billId: Int): Flow<Bill?> {
        return billRepo.getBillById(billId)
    }
}