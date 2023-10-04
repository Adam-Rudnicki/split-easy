package com.mammuten.spliteasy.data.model.relation.manytomany.groupwithusers

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

import com.mammuten.spliteasy.data.model.associativeentity.groupusercrossref.GroupUserCrossRef
import com.mammuten.spliteasy.data.model.entity.group.Group
import com.mammuten.spliteasy.data.model.entity.user.User

data class GroupWithUsers (
    @Embedded val group: Group,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
        associateBy = Junction(GroupUserCrossRef::class)
    )
    val users: List<User>
)