package com.mammuten.spliteasy.data.mapper

import com.mammuten.spliteasy.data.source.local.entity.group.GroupEntity
import com.mammuten.spliteasy.domain.model.Group

fun Group.asEntity(): GroupEntity {
    return GroupEntity(
        id = id,
        name = name,
        description = description,
    )
}

fun List<Group>.asEntityList(): List<GroupEntity> = this.map { it.asEntity() }

fun GroupEntity.asModel(): Group {
    return Group(
        id = id,
        name = name,
        description = description,
        created = created
    )
}

fun List<GroupEntity>.asModelList(): List<Group> = this.map { it.asModel() }
