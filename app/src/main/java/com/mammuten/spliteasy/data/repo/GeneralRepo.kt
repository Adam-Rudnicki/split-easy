package com.mammuten.spliteasy.data.repo

import com.mammuten.spliteasy.data.mapper.asModel
import com.mammuten.spliteasy.data.source.local.LocalSplitDataSource
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.model.Member
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GeneralRepo(
    private val dataSource: LocalSplitDataSource
) {
    fun getMembersAndContributionsInBill(billId: Int): Flow<Map<Member, Contribution>> =
        dataSource.getMembersAndContributionsInBill(billId).map {
            it.entries.associate { (key, value) -> key.asModel() to value.asModel() }
        }

    fun getAllMembersInGroupAndContributionsInBill(
        groupId: Int, billId: Int
    ): Flow<Map<Member, Contribution?>> =
        dataSource.getAllMembersInGroupAndContributionsInBill(groupId, billId).map {
            it.entries.associate { (key, value) -> key.asModel() to value?.asModel() }
        }
}