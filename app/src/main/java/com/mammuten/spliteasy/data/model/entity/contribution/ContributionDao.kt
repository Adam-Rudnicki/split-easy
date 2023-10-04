package com.mammuten.spliteasy.data.model.entity.contribution

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ContributionDao {
    @Upsert
    suspend fun upsertContribution(contribution: Contribution)

    @Delete
    suspend fun deleteContribution(contribution: Contribution)

    @Query("SELECT * FROM contributions WHERE billId = :billId AND userId = :userId")
    suspend fun loadContributionById(billId: Int, userId: Int): Contribution

//    @Query("SELECT * FROM contributions WHERE billId = :billId AND userId = :userId")
//    fun loadContributionById(billId: Int, userId: Int): Flow<Contribution>
}