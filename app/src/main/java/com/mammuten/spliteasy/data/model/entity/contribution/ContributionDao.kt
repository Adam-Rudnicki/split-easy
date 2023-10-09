package com.mammuten.spliteasy.data.model.entity.contribution

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ContributionDao {
    @Upsert
    suspend fun upsertContribution(contributionEntity: ContributionEntity)

    @Delete
    suspend fun deleteContribution(contributionEntity: ContributionEntity)

    @Query("SELECT * FROM contributions WHERE billId = :billId AND memberId = :memberId")
    suspend fun loadContributionById(billId: Int, memberId: Int): ContributionEntity

//    @Query("SELECT * FROM contributions WHERE billId = :billId AND memberId = :memberId")
//    fun loadContributionById(billId: Int, memberId: Int): Flow<Contribution>
}