package com.mammuten.spliteasy.data.source.local.entity.contribution

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContributionDao {
    @Upsert
    suspend fun upsertContribution(contributionEntity: ContributionEntity)

    @Delete
    suspend fun deleteContribution(contributionEntity: ContributionEntity)

    @Upsert
    suspend fun upsertContributions(contributionEntities: List<ContributionEntity>)

    @Delete
    suspend fun deleteContributions(contributionEntities: List<ContributionEntity>)

    @Transaction
    suspend fun updateContributions(
        contributionEntitiesToUpsert: List<ContributionEntity>,
        contributionEntitiesToDelete: List<ContributionEntity>
    ) {
        upsertContributions(contributionEntitiesToUpsert)
        deleteContributions(contributionEntitiesToDelete)
    }

    @Query("SELECT * FROM ${ContributionEntity.TABLE_NAME} WHERE billId = :billId AND memberId = :memberId")
    fun getContributionByBillIdAndMemberId(billId: Int, memberId: Int): Flow<ContributionEntity?>

    @Query("SELECT * FROM ${ContributionEntity.TABLE_NAME} WHERE billId = :billId")
    fun getContributionsByBillId(billId: Int): Flow<List<ContributionEntity>>
}