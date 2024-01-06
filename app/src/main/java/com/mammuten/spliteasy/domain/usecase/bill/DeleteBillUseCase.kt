package com.mammuten.spliteasy.domain.usecase.bill

import com.mammuten.spliteasy.data.repo.BillRepo
import com.mammuten.spliteasy.domain.model.Bill

class DeleteBillUseCase(
    private val billRepo: BillRepo
) {
    suspend operator fun invoke(bill: Bill) {
        billRepo.deleteBill(bill)
    }
}