package com.mammuten.spliteasy.data.model.relation.onetomany.userwithcontributions

import androidx.room.Embedded
import androidx.room.Relation

import com.mammuten.spliteasy.data.model.entity.user.MemberEntity
import com.mammuten.spliteasy.data.model.entity.contribution.ContributionEntity

data class UserWithContributions (
    @Embedded val user: MemberEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val contributions: List<ContributionEntity>
)