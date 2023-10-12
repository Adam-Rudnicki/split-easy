package com.mammuten.spliteasy.data.source.local.model.relation.onetomany.groupwithmembers

import androidx.room.Embedded
import androidx.room.Relation

import com.mammuten.spliteasy.data.source.local.model.entity.group.GroupEntity
import com.mammuten.spliteasy.data.source.local.model.entity.member.MemberEntity

data class GroupWithMembers(
    @Embedded val group: GroupEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val members: List<MemberEntity>
)