package com.mammuten.spliteasy.domain.usecase.group

import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.repository.GroupRepository
import com.mammuten.spliteasy.util.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGroupWithBillsFlowUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    operator fun invoke(groupId: Int): Flow<Resource<List<Bill>>> {
        return groupRepository.getGroupWithBillsFlow(groupId).map { bills ->
            Resource.Success(bills)
        }
    }
}