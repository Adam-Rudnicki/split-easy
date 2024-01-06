package com.mammuten.spliteasy.data.mapper

import com.mammuten.spliteasy.data.source.local.entity.bill.BillEntity
import com.mammuten.spliteasy.data.source.local.entity.group.GroupEntity
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Group

// Group mappers

fun Group.asEntity(): GroupEntity {
    return GroupEntity(
        id = id,
        name = name,
        description = description,
    )
}

fun GroupEntity.asModel(): Group {
    return Group(
        id = id,
        name = name,
        description = description,
        created = created
    )
}

fun List<GroupEntity>.asGroupModelList(): List<Group> = this.map { it.asModel() }

// Bill mappers

fun Bill.asEntity(): BillEntity {
    return BillEntity(
        id = id,
        groupId = groupId,
        name = name,
        description = description,
        amount = amount,
        date = date
    )
}

fun BillEntity.asModel(): Bill {
    return Bill(
        id = id,
        groupId = groupId,
        name = name,
        description = description,
        amount = amount,
        date = date
    )
}

fun List<BillEntity>.asBillModelList(): List<Bill> = this.map { it.asModel() }
