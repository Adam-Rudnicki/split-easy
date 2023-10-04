package com.mammuten.spliteasy.data.model.relation.onetomany.billwithcontributions

import androidx.room.Embedded
import androidx.room.Relation

import com.mammuten.spliteasy.data.model.entity.bill.Bill
import com.mammuten.spliteasy.data.model.entity.contribution.Contribution

data class BillWithContributions(
    @Embedded val bill: Bill,
    @Relation(
        parentColumn = "id",
        entityColumn = "billId"
    )
    val contributions: List<Contribution>
)