package com.mammuten.spliteasy.data.model.relation.onetomany.userwithcontributions

import androidx.room.Embedded
import androidx.room.Relation

import com.mammuten.spliteasy.data.model.entity.user.User
import com.mammuten.spliteasy.data.model.entity.contribution.Contribution

data class UserWithContributions (
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val contributions: List<Contribution>
)