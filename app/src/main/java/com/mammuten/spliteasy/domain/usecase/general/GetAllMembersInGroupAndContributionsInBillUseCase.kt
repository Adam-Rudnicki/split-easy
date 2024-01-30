package com.mammuten.spliteasy.domain.usecase.general

import com.mammuten.spliteasy.data.repo.GeneralRepo
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.model.Member
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllMembersInGroupAndContributionsInBillUseCase(
    private val generalRepo: GeneralRepo
) {
    operator fun invoke(groupId: Int, billId: Int): Flow<Map<Member, Contribution?>> {
        return generalRepo.getAllMembersInGroupAndContributionsInBill(groupId, billId)
    }
}
