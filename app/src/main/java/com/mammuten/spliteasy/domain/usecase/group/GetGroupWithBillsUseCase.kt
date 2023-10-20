package com.mammuten.spliteasy.domain.usecase.group

import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.repository.GroupRepository
import com.mammuten.spliteasy.util.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGroupWithBillsUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupId: Int): Resource<List<Bill>> {
        return Resource.Success(groupRepository.getGroupWithBills(groupId))
    }
}