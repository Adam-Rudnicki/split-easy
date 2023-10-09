package com.mammuten.spliteasy.data.model.relation.onetomany.memberwithcontributions

import androidx.room.Embedded
import androidx.room.Relation

import com.mammuten.spliteasy.data.model.entity.member.MemberEntity
import com.mammuten.spliteasy.data.model.entity.contribution.ContributionEntity

data class MemberWithContributions (
    @Embedded val member: MemberEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "memberId"
    )
    val contributions: List<ContributionEntity>
)