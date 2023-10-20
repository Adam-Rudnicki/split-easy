package com.mammuten.spliteasy.domain.usecase.group

import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.repository.GroupRepository
import com.mammuten.spliteasy.util.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGroupWithBillsFlowUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    operator fun invoke(groupId: Int): Flow<Resource<List<Bill>>> {
        return flow {
            groupRepository.getGroupWithBillsFlow(groupId).collect { bills ->
                emit(Resource.Loading())
                emit(Resource.Success(bills))
            }
        }
    }
}