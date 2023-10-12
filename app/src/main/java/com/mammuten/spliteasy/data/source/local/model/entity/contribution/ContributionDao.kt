package com.mammuten.spliteasy.data.source.local.model.entity.contribution

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

}