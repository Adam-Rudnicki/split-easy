package com.mammuten.spliteasy.data.mapper

import com.mammuten.spliteasy.data.model.entity.user.MemberEntity
import com.mammuten.spliteasy.domain.model.User

class UserMapper: Mapper<MemberEntity, User> {
    override fun toDomain(entity: MemberEntity): User {
        return User(
            id = entity.id,
            username = entity.username,
            nickname = entity.nickname,
            groups = emptyList(),
            contributions = emptyList()
        )
    }

    override fun toEntity(domain: User): MemberEntity {
        return MemberEntity(
            id = domain.id,
            username = domain.username,
            nickname = domain.nickname
        )
    }
}