package com.mammuten.spliteasy.data.mapper

import com.mammuten.spliteasy.data.source.local.model.entity.contribution.ContributionEntity
import com.mammuten.spliteasy.domain.model.dto.Contribution

class ContributionMapper : Mapper<ContributionEntity, Contribution> {
    override fun toDomain(entity: ContributionEntity): Contribution {
        return Contribution(
            billId = entity.billId,
            memberId = entity.memberId,
            amountPaid = entity.amountPaid,
            amountOwed = entity.amountOwed
        )
    }

    override fun toEntity(domain: Contribution): ContributionEntity {
        return ContributionEntity(
            billId = domain.billId,
            memberId = domain.memberId,
            amountPaid = domain.amountPaid,
            amountOwed = domain.amountOwed
        )
    }
}