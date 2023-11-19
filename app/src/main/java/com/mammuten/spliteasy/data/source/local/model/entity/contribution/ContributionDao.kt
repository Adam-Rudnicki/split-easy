package com.mammuten.spliteasy.data.source.local.model.entity.contribution

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContributionDao {
    @Upsert
    suspend fun upsertContributions(vararg contributions: ContributionEntity)

    @Delete
    suspend fun deleteContribution(vararg contributions: ContributionEntity)

    @Query("SELECT * FROM contributions WHERE billId = :billId")
    suspend fun loadContributionsByBillId(billId: Int): List<ContributionEntity>

    @Query("SELECT * FROM contributions WHERE billId = :billId")
    fun loadContributionsByBillIdAsFlow(billId: Int): Flow<List<ContributionEntity>>

    @Query("SELECT * FROM contributions WHERE memberId = :memberId")
    suspend fun loadContributionsByMemberId(memberId: Int): List<ContributionEntity>

    @Query("SELECT * FROM contributions WHERE memberId = :memberId")
    fun loadContributionsByMemberIdAsFlow(memberId: Int): Flow<List<ContributionEntity>>

}