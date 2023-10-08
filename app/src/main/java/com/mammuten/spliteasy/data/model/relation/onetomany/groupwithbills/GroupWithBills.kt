package com.mammuten.spliteasy.data.model.relation.onetomany.groupwithbills

import androidx.room.Embedded
import androidx.room.Relation

import com.mammuten.spliteasy.data.model.entity.bill.BillEntity
import com.mammuten.spliteasy.data.model.entity.group.GroupEntity

data class GroupWithBills(
    @Embedded val group: GroupEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val playlists: List<BillEntity>
)