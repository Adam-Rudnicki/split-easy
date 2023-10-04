package com.mammuten.spliteasy.data.model.relation.onetomany.groupwithbills

import androidx.room.Embedded
import androidx.room.Relation

import com.mammuten.spliteasy.data.model.entity.bill.Bill
import com.mammuten.spliteasy.data.model.entity.group.Group

data class GroupWithBills(
    @Embedded val user: Group,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val playlists: List<Bill>
)