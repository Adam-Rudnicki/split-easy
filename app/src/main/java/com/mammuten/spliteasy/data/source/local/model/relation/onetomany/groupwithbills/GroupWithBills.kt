package com.mammuten.spliteasy.data.source.local.model.relation.onetomany.groupwithbills

import androidx.room.Embedded
import androidx.room.Relation

import com.mammuten.spliteasy.data.source.local.model.entity.bill.BillEntity
import com.mammuten.spliteasy.data.source.local.model.entity.group.GroupEntity

data class GroupWithBills(
    @Embedded val group: GroupEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val bills: List<BillEntity>
)