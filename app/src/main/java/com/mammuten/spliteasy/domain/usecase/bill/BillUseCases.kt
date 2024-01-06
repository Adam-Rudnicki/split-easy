package com.mammuten.spliteasy.domain.usecase.bill

data class BillUseCases(
    val upsertBillUseCase: UpsertBillUseCase,
    val deleteBillUseCase: DeleteBillUseCase,
    val getBillByIdUseCase: GetBillByIdUseCase,
    val getBillsByGroupIdUseCase: GetBillsByGroupIdUseCase
)