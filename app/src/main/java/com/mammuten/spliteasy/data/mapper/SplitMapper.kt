package com.mammuten.spliteasy.data.mapper

import com.mammuten.spliteasy.data.source.local.entity.bill.BillEntity
import com.mammuten.spliteasy.data.source.local.entity.group.GroupEntity
import com.mammuten.spliteasy.data.source.local.entity.member.MemberEntity
import com.mammuten.spliteasy.data.source.local.entity.contribution.ContributionEntity
import com.mammuten.spliteasy.data.source.local.entity.user.UserEntity
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.model.User

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

// Member mappers
fun Member.asEntity(): MemberEntity {
    return MemberEntity(
        id = id,
        groupId = groupId,
        userId = userId,
        name = name
    )
}

fun MemberEntity.asModel(): Member {
    return Member(
        id = id,
        groupId = groupId,
        userId = userId,
        name = name
    )
}

fun List<MemberEntity>.asMemberModelList(): List<Member> = this.map { it.asModel() }
fun List<Member>.asMemberEntityList(): List<MemberEntity> = this.map { it.asEntity() }

// Contribution mappers
fun Contribution.asEntity(): ContributionEntity {
    return ContributionEntity(
        billId = billId,
        memberId = memberId,
        amountPaid = amountPaid,
        amountOwed = amountOwed
    )
}

fun ContributionEntity.asModel(): Contribution {
    return Contribution(
        billId = billId,
        memberId = memberId,
        amountPaid = amountPaid,
        amountOwed = amountOwed
    )
}

fun List<ContributionEntity>.asContributionModelList(): List<Contribution> =
    this.map { it.asModel() }

fun List<Contribution>.asContributionEntityList(): List<ContributionEntity> =
    this.map { it.asEntity() }

// User mappers
fun User.asEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        surname = surname,
        nick = nick,
        phone = phone,
        description = description
    )
}

fun UserEntity.asModel(): User {
    return User(
        id = id,
        name = name,
        surname = surname,
        nick = nick,
        phone = phone,
        description = description
    )
}

fun List<UserEntity>.asUserModelList(): List<User> = this.map { it.asModel() }