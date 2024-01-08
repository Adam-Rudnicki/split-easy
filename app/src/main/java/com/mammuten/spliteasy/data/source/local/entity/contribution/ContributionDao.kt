package com.mammuten.spliteasy.data.source.local.entity.contribution

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContributionDao {
    @Upsert
    suspend fun upsertContribution(contributionEntity: ContributionEntity)

    @Delete
    suspend fun deleteContribution(contributionEntity: ContributionEntity)

    @Query("SELECT * FROM ${ContributionEntity.TABLE_NAME} WHERE billId = :billId AND memberId = :memberId")
    fun getContributionByBillIdAndMemberId(billId: Int, memberId: Int): Flow<ContributionEntity?>

    @Query("SELECT * FROM ${ContributionEntity.TABLE_NAME} WHERE billId = :billId")
    fun getContributionsByBillId(billId: Int): Flow<List<ContributionEntity>>

    @Query("SELECT * FROM ${ContributionEntity.TABLE_NAME} WHERE memberId = :memberId")
    fun getContributionsByMemberId(memberId: Int): Flow<List<ContributionEntity>>
}