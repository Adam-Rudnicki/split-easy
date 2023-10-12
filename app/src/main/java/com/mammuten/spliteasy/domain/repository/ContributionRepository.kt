package com.mammuten.spliteasy.domain.repository

import com.mammuten.spliteasy.domain.model.dto.Contribution

interface ContributionRepository {
    suspend fun upsertContribution(contribution: Contribution)
    suspend fun deleteContribution(contribution: Contribution)
}