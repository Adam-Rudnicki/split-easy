package com.mammuten.spliteasy.data.model.relation.manytomany.userwithgroups

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

import com.mammuten.spliteasy.data.model.associativeentity.GroupUserCrossRef
import com.mammuten.spliteasy.data.model.entity.group.Group
import com.mammuten.spliteasy.data.model.entity.user.User

data class UserWithGroups (
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId",
        associateBy = Junction(GroupUserCrossRef::class)
    )
    val groups: List<Group>
)