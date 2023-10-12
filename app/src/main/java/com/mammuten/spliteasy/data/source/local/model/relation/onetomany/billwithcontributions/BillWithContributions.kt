package com.mammuten.spliteasy.data.source.local.model.relation.onetomany.billwithcontributions

import androidx.room.Embedded
import androidx.room.Relation

import com.mammuten.spliteasy.data.source.local.model.entity.bill.BillEntity
import com.mammuten.spliteasy.data.source.local.model.entity.contribution.ContributionEntity

data class BillWithContributions(
    @Embedded val bill: BillEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "billId"
    )
    val contributions: List<ContributionEntity>
)