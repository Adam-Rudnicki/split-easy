package com.mammuten.spliteasy.data.model.associativeentity

import androidx.room.Entity
import androidx.room.ForeignKey

import com.mammuten.spliteasy.data.model.entity.group.Group
import com.mammuten.spliteasy.data.model.entity.user.User

@Entity(
    tableName = "group_user_cross_ref",
    primaryKeys = ["groupId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = Group::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GroupUserCrossRef (
    val groupId: Int,
    val userId: Int
)