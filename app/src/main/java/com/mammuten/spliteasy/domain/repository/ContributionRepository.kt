package com.mammuten.spliteasy.domain.repository

import com.mammuten.spliteasy.domain.model.Contribution
import kotlinx.coroutines.flow.Flow

interface ContributionRepository {
    suspend fun upsertContribution(contribution: Contribution)
    suspend fun deleteContribution(contribution: Contribution)

    suspend fun getContributionsByBillId(billId: Int): List<Contribution>

    fun getContributionsByBillIdAsFlow(billId: Int): Flow<List<Contribution>>

    suspend fun getContributionsByMemberId(memberId: Int): List<Contribution>

    fun getContributionsByMemberIdAsFlow(memberId: Int): Flow<List<Contribution>>

}