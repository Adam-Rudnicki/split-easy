package com.mammuten.spliteasy.data.mapper

import com.mammuten.spliteasy.data.model.entity.member.MemberEntity
import com.mammuten.spliteasy.domain.model.Member

class MemberMapper: Mapper<MemberEntity, Member> {
    override fun toDomain(entity: MemberEntity): Member {
        return Member(
            id = entity.id,
            groupId = entity.groupId,
            nickname = entity.nickname
        )
    }

    override fun toEntity(domain: Member): MemberEntity {
        return MemberEntity(
            id = domain.id,
            groupId = domain.groupId,
            nickname = domain.nickname
        )
    }
}