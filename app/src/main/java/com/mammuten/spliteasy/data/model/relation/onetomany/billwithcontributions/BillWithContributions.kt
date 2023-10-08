package com.mammuten.spliteasy.data.model.relation.onetomany.billwithcontributions

import androidx.room.Embedded
import androidx.room.Relation

import com.mammuten.spliteasy.data.model.entity.bill.BillEntity
import com.mammuten.spliteasy.data.model.entity.contribution.ContributionEntity

data class BillWithContributions(
    @Embedded val bill: BillEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "billId"
    )
    val contributions: List<ContributionEntity>
)